package com.example.mymoviedb.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mymoviedb.R
import com.example.mymoviedb.databinding.FragmentProfileBinding
import com.example.mymoviedb.userdatabase.User
import com.example.mymoviedb.userdatabase.UserDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get()= _binding!!
    val sharedPreFile = "login_account"
    private var userDb: UserDatabase? = null

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
        userDb = UserDatabase.getInstance(this.requireContext())
        val sharedPreferences: SharedPreferences = this.requireActivity().getSharedPreferences(sharedPreFile, Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "-")
        val password = sharedPreferences.getString("password", "-")


        binding.etBirthday.transformIntoDatePicker(requireContext(), "MM/dd/yyyy", Date())

        binding.btnUpdate.setOnClickListener {
        val username = binding.etUsername.text.toString()
        val realName = binding.etRealName.text.toString()
        val birthday = binding.etBirthday.text.toString()
        val address = binding.etAddress.text.toString()
            when{
                username.isEmpty() -> binding.etUsername.error = "input new username"
                realName.isEmpty() -> binding.etRealName.error = "input your real name"
                birthday.isEmpty() -> binding.etBirthday.error = "pick your birthday"
                address.isEmpty() -> binding.etAddress.error = "input your address"
                else -> {
                    GlobalScope.async{
                        val dataUpdate = email?.let { it1 -> User(it1, username, password, realName, birthday, address) }
                        val update = dataUpdate?.let { it1 -> userDb?.userDao()?.updateUser(it1) }

                        activity?.runOnUiThread{
                            if(update != 0){
                                Toast.makeText(it.context, "Update Success", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
                            }else{
                                Toast.makeText(it.context, "error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

            }
        }
    }

    fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null){
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