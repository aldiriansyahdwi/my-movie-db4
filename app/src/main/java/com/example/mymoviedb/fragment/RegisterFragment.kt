package com.example.mymoviedb.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mymoviedb.R
import com.example.mymoviedb.databinding.FragmentRegisterBinding
import com.example.mymoviedb.userdatabase.User
import com.example.mymoviedb.userdatabase.UserDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class RegisterFragment : Fragment() {

    private var userDb: UserDatabase? = null
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

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

        userDb = UserDatabase.getInstance(this.requireContext())
        binding.btnRegister.setOnClickListener {
            val username: String = binding.etUsername.text.toString()
            val email: String = binding.etEmail.text.toString()
            val password: String = binding.etPassword.text.toString()
            val confirmPassword: String = binding.etConfirmPassword.text.toString()

            val userData = User(email, username, password, null, null, null)
            when {
                username.isEmpty() -> { binding.etUsername.error = "username cannot be empty" }
                email.isEmpty() -> { binding.etEmail.error = "email cannot be empty" }
//                checkEmailExists(userData) -> { binding.etEmail.error = "email already exists" }
                password.isEmpty() -> { binding.etPassword.error = "password cannot be empty" }
                confirmPassword != password -> { binding.etConfirmPassword.error = "password doesnt match" }
                else -> GlobalScope . async {
                    val result = userDb?.userDao()?.insertUser(userData)
                    activity?.runOnUiThread {
                        if (result != 0.toLong()) {
                            Toast.makeText(requireContext(), "Register Successfully", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        } else {
                            Toast.makeText(requireContext(), "Register failed", Toast.LENGTH_SHORT)
                                .show()
                        }
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