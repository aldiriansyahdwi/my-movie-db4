package com.example.mymoviedb.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mymoviedb.R
import com.example.mymoviedb.databinding.FragmentLoginBinding
import com.example.mymoviedb.userdatabase.UserDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var userDb: UserDatabase? = null
    private val sharedPreFile = "login_account"

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
        val sharedPreferences: SharedPreferences = this.requireActivity().getSharedPreferences(sharedPreFile, Context.MODE_PRIVATE)
        userDb = UserDatabase.getInstance(this.requireContext())

        val sharedEmail = sharedPreferences.getString("email", "-")
        val sharedUsername = sharedPreferences.getString("username", "-")
        if(sharedEmail != "-" && sharedUsername != "-"){
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            GlobalScope.async {
                val verification = userDb?.userDao()?.verifyLogin(email, password)
                activity?.runOnUiThread {
                    if(verification.isNullOrEmpty()){
                        Toast.makeText(it.context, "account not found", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("email", email)
                        editor.putString("password", password)
                        editor.putString("username", verification[0].username)
                        editor.apply()
                        Toast.makeText(it.context, "hello ${verification[0].username}", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
            }

        }
    }
}