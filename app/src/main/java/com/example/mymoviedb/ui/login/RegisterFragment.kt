package com.example.mymoviedb.ui.login

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.mymoviedb.R
import com.example.mymoviedb.databinding.FragmentRegisterBinding
import com.example.mymoviedb.userdatabase.User
import com.example.mymoviedb.userdatabase.UserDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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

//            val userData : User = runBlocking {
//            User(email, username, password, null, null, null, getBitmap())
//            }

            when {
                username.length <= 4 -> { binding.etUsername.error = "username must be at least 4 characters" }
                email.isEmpty() -> { binding.etEmail.error = "email cannot be empty" }
                password.length <= 4 -> { binding.etPassword.error = "password must be at least 4 characters" }
                confirmPassword != password -> { binding.etConfirmPassword.error = "password doesn't match" }
                else ->  {
                    lifecycleScope.launch {
                        val result = viewModel.saveUser(User(email, username, password, null, null, null, getBitmap()))
                        if (result != 0.toLong()) {
                            Toast.makeText(
                                requireContext(),
                                "Register Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
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

    private suspend fun getBitmap(): Bitmap {
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(R.drawable.ic_app_main)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }
}