package com.example.mymoviedb.ui.profile

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymoviedb.R
import com.example.mymoviedb.databinding.FragmentProfileBinding
import com.example.mymoviedb.data.userdatabase.User
import com.example.mymoviedb.data.userdatabase.UserDatabase
import com.example.mymoviedb.utils.PermissionUtils
import com.example.mymoviedb.utils.StorageUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var image: Uri

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bitmap = result.data?.extras?.get("data")

                val uri = StorageUtils.savePhotoToExternalStorage(
                    requireActivity().contentResolver,
                    UUID.randomUUID().toString(),
                    bitmap as Bitmap?
                )
                uri?.let {
                    loadImage(it)
                    image = it
                }
            }
        }

    private fun loadImage(uri: Uri) {
        binding.ivProfile.setImageURI(uri)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }

    private fun getRequiredPermission(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etBirthday.transformIntoDatePicker(requireContext(), "MM/dd/yyyy", Date())

        binding.btnLogout.setOnClickListener {
            viewModel.deleteLogin()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            Toast.makeText(requireContext(), "Log Out", Toast.LENGTH_SHORT).show()
        }

        viewModel.getEmail().observe(viewLifecycleOwner) {
            viewModel.checkEmail(it)
            viewModel.user.observe(viewLifecycleOwner) { user ->
                binding.apply {
                    try {
                        ivProfile.setImageBitmap(user[0].profilePhoto)
                        etUsername.setText(user[0].username ?: "")
                        etRealName.setText(user[0].realName ?: "")
                        etBirthday.setText(user[0].birthDate ?: "")
                        etAddress.setText(user[0].address ?: "")
                    } catch (exception: Exception) {
                    }

                    ivProfile.setOnClickListener {
                        tryOpenCamera()
                    }
                    btnUpdate.setOnClickListener {
                        val username = binding.etUsername.text.toString()
                        val realName = binding.etRealName.text.toString()
                        val birthday = binding.etBirthday.text.toString()
                        val address = binding.etAddress.text.toString()
                        checkInput(username, realName, birthday, address, user[0])
                    }
                }
            }
        }

    }

    private fun tryOpenCamera() {
        if (PermissionUtils.isPermissionsGranted(
                requireActivity(),
                getRequiredPermission()
            )
        ) {
            openCamera()
        }
    }

    private fun checkInput(
        username: String,
        realName: String,
        birthday: String,
        address: String,
        user: User
    ) {
        when {
            username.isEmpty() -> binding.etUsername.error =
                "input new username"
            realName.isEmpty() -> binding.etRealName.error =
                "input your real name"
            birthday.isEmpty() -> binding.etBirthday.error =
                "pick your birthday"
            address.isEmpty() -> binding.etAddress.error = "input your address"
            else -> {
                val imgProfile: Bitmap = binding.ivProfile.drawable.toBitmap()

//                if(image != null) {
//                    imgProfile = BitmapFactory.decodeStream(
//                        requireActivity().contentResolver.openInputStream(image)
//                    )
//                } else {


                viewModel.updateData(
                    User(
                        user.email,
                        username, user.password, realName,
                        birthday, address, imgProfile
                    )
                )
                viewModel.updatedUser.observe(viewLifecycleOwner) { update ->

                    if (update != 0) {
                        user.username?.let { it1 ->
                            viewModel.saveDataStore(
                                user.email,
                                it1
                            )
                        }
                        Toast.makeText(
                            context,
                            "Update Success",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun EditText.transformIntoDatePicker(
        context: Context,
        format: String,
        maxDate: Date? = null
    ) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            myCalendar.apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, day)
            }
            val sdf = SimpleDateFormat(format, Locale.UK)
            setText(sdf.format(myCalendar.time))
        }

        setOnClickListener {
            DatePickerDialog(
                context,
                datePickerOnDataListener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }
        }
    }
}