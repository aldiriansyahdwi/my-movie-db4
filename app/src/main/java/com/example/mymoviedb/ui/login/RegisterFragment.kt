package com.example.mymoviedb.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymoviedb.R
import com.example.mymoviedb.databinding.FragmentRegisterBinding
import com.example.mymoviedb.userdatabase.User
import com.example.mymoviedb.userdatabase.UserDatabase

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, RegisterViewModelFactory(UserDatabase.getInstance(this.requireContext())))[RegisterViewModel::class.java]

        binding.btnRegister.setOnClickListener {
            val username: String = binding.etUsername.text.toString()
            val email: String = binding.etEmail.text.toString()
            val password: String = binding.etPassword.text.toString()
            val confirmPassword: String = binding.etConfirmPassword.text.toString()

            val userData = User(email, username, password, null, null, null)

            when {
                username.length <= 4 -> { binding.etUsername.error = "username must be at least 4 characters" }
                email.isEmpty() -> { binding.etEmail.error = "email cannot be empty" }
//                checkEmailExists(userData) -> { binding.etEmail.error = "email already exists" }
                password.length <= 4 -> { binding.etPassword.error = "password must be at least 4 characters" }
                confirmPassword != password -> { binding.etConfirmPassword.error = "password doesn't match" }
                else ->  {
                    val result = viewModel.saveUser(userData)
                    if (result != 0.toLong()) {
                        Toast.makeText(requireContext(), "Register Successfully", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    } else {
                        Toast.makeText(requireContext(), "Register failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

//    private fun checkEmailExists(userData: User?): Boolean{
//        var result: List<User>? = null
//        GlobalScope.async{
//            result = userDb?.userDao()?.checkEmail(userData?.email)
//        }
//        return when {
//            result.isNullOrEmpty() -> true
//            else -> false
//        }
//    }
}