package com.example.mymoviedb.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import com.example.mymoviedb.R
import com.example.mymoviedb.databinding.FragmentLoginBinding
import com.example.mymoviedb.repository.UserDataStoreManager
import com.example.mymoviedb.userdatabase.UserDatabase
import com.example.mymoviedb.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel
    private lateinit var pref: UserDataStoreManager
//    private val sharedPreFile = "login_account"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this,
            LoginViewModelFactory(
            UserDatabase.getInstance(this.requireContext()),
            UserDataStoreManager(this.requireContext())))[LoginViewModel::class.java]

        viewModel.apply {
            getUsername().observe(viewLifecycleOwner){
                if (it != "-"){
                    requireActivity().runOnUiThread {
                        findNavController().navigate(R.id.homeFragment)
                    }
                }
            }
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            val verification = viewModel.verifyLogin(email, password)
            if(verification.isEmpty()){
                Toast.makeText(it.context, "account not found", Toast.LENGTH_SHORT).show()
            }
            else{
                viewModel.saveDataStore(email, verification[0].username.toString())
                Toast.makeText(it.context, "hello ${verification[0].username}", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }
}