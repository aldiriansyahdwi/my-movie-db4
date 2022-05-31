package com.example.mymoviedb.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.datastore.dataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymoviedb.R
import com.example.mymoviedb.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {
//    private var _binding: FragmentLoginBinding? = null
//    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModel()
//    private val sharedPreFile = "login_account"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        _binding = FragmentLoginBinding.inflate(inflater, container, false)
//        return binding.root
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )
            setContent{
                MaterialTheme {

                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.tvRegister.setOnClickListener {
//            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
//        }
//
//        binding.btnLogin.setOnClickListener {
//
//        }
//
    }

    fun alreadyLogin(){
        viewModel.getUsername().observe(viewLifecycleOwner) {
            if (it != "-") {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    fun loginAction(){
//        val email = binding.etEmail.text.toString()
//        val password = binding.etPassword.text.toString()
//        viewModel.verifyLogin(email, password)
//        viewModel.user.observe(viewLifecycleOwner) { user ->
//            if (user.isEmpty()) {
//                Toast.makeText(requireContext(), "account not found", Toast.LENGTH_SHORT).show()
//            } else {
//                viewModel.saveDataStore(email, user[0].username.toString())
//                Toast.makeText(requireContext(), "hello ${user[0].username}", Toast.LENGTH_SHORT).show()
//                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
//            }
//        }
    }
}