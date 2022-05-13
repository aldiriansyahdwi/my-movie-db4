package com.example.mymoviedb.ui.home

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.mymoviedb.R
import com.example.mymoviedb.databinding.FragmentProfileBinding
import com.example.mymoviedb.repository.UserDataStoreManager
import com.example.mymoviedb.userdatabase.User
import com.example.mymoviedb.userdatabase.UserDatabase
import com.example.mymoviedb.utils.PermissionUtils
import com.example.mymoviedb.utils.StorageUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get()= _binding!!
    private lateinit var viewModel: ProfileViewModel
    private var image : Uri? = null

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

    private fun getRequiredPermission(): Array<String>{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        }else{
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

        viewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory(
                UserDatabase.getInstance(this.requireContext()),
                UserDataStoreManager(this.requireContext())
            )
        )[ProfileViewModel::class.java]

        binding.etBirthday.transformIntoDatePicker(requireContext(), "MM/dd/yyyy", Date())

        viewModel.getEmail().observe(viewLifecycleOwner) {
            val oldUser = viewModel.checkEmail(it)
            binding.apply {
                try {
                    ivProfile.setImageBitmap(oldUser[0].profilePhoto)
                    etUsername.setText(oldUser[0].username ?: "")
                    etRealName.setText(oldUser[0].realName ?: "")
                    etBirthday.setText(oldUser[0].birthDate ?: "")
                    etAddress.setText(oldUser[0].address ?: "")
                } catch (exception: Exception) {
                }

                ivProfile.setOnClickListener {
                    if (PermissionUtils.isPermissionsGranted(
                            requireActivity(),
                            getRequiredPermission()
                        )
                    ) {
                        openCamera()

                    }

                    btnUpdate.setOnClickListener {
                        val username = binding.etUsername.text.toString()
                        val realName = binding.etRealName.text.toString()
                        val birthday = binding.etBirthday.text.toString()
                        val address = binding.etAddress.text.toString()
                        when {
                            username.isEmpty() -> binding.etUsername.error = "input new username"
                            realName.isEmpty() -> binding.etRealName.error = "input your real name"
                            birthday.isEmpty() -> binding.etBirthday.error = "pick your birthday"
                            address.isEmpty() -> binding.etAddress.error = "input your address"
                            else -> {
                                val imgProfile: Bitmap = BitmapFactory.decodeStream(requireActivity().contentResolver.openInputStream(image!!))
                                //todo fix update can execute without change profile
                                val update = viewModel.updateData(
                                    User(
                                        oldUser[0].email,
                                        username, oldUser[0].password, realName,
                                        birthday, address, imgProfile
                                    )
                                )

                                if (update != 0) {
                                    oldUser[0].username?.let { it1 ->
                                        viewModel.saveDataStore(
                                            oldUser[0].email,
                                            it1
                                        )
                                    }
                                    Toast.makeText(context, "Update Success", Toast.LENGTH_SHORT)
                                        .show()
                                    findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
                                } else {
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
            binding.btnLogout.setOnClickListener {
                viewModel.deleteLogin()
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                Toast.makeText(requireContext(), "Log Out", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null){
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