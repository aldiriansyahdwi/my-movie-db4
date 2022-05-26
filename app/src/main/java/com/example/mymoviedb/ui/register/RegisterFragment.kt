package com.example.mymoviedb.ui.register

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.mymoviedb.R
import com.example.mymoviedb.databinding.FragmentRegisterBinding
import com.example.mymoviedb.data.userdatabase.User
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModel()

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

        binding.btnRegister.setOnClickListener {
            val username: String = binding.etUsername.text.toString()
            val email: String = binding.etEmail.text.toString()
            val password: String = binding.etPassword.text.toString()
            val confirmPassword: String = binding.etConfirmPassword.text.toString()
            checkInput(username, email, password, confirmPassword)
        }
    }

    private fun checkInput(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        when {
            username.length <= 4 -> {
                binding.etUsername.error = "username must be at least 4 characters"
            }
            email.isEmpty() -> {
                binding.etEmail.error = "email cannot be empty"
            }
            password.length <= 4 -> {
                binding.etPassword.error = "password must be at least 4 characters"
            }
            confirmPassword != password -> {
                binding.etConfirmPassword.error = "password doesn't match"
            }
            else -> {
                lifecycleScope.launch {
                    viewModel.saveUser(
                        User(
                            email,
                            username,
                            password,
                            null,
                            null,
                            null,
                            getBitmap()
                        )
                    )
                    viewModel.savedUser.observe(viewLifecycleOwner) {
                        if (it != 0.toLong()) {
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