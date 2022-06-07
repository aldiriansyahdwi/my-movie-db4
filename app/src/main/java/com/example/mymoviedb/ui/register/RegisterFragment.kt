package com.example.mymoviedb.ui.register

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.mymoviedb.R
import com.example.mymoviedb.data.userdatabase.User
import com.example.mymoviedb.ui.theme.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )
            setContent {
                MyFirstComposeTheme {
                    Column {
                        RegisterHeader()
                        RegisterBody(viewModel = viewModel)
                        RegisterButtonAction {
                            viewModel.apply {
                                checkInput(
                                    usernameInput.value,
                                    emailInput.value,
                                    passwordInput.value,
                                    confirmPasswordInput.value
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkInput(username: String, email: String, password: String, confirmPassword: String) {
        when {
            username.isEmpty() -> {
                Toast.makeText(requireContext(), "please input username", Toast.LENGTH_SHORT).show()
            }
            email.isEmpty() -> {
                Toast.makeText(requireContext(), "please input email", Toast.LENGTH_SHORT).show()
            }
            password.isEmpty() -> {
                Toast.makeText(requireContext(), "please input password", Toast.LENGTH_SHORT).show()
            }
            confirmPassword != password -> {
                Toast.makeText(requireContext(), "password does not match", Toast.LENGTH_SHORT).show()
            }
            else -> {
                lifecycleScope.launch {
                    viewModel.saveUser(
                        User(email, username, password, null, null, null, getBitmap())
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

@Composable
fun RegisterHeader(){
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.heightIn(min = 64.dp, max = 128.dp))
        Text(text = "Register",
            style = TextStyle(color = Gray, fontSize = 42.sp),
            modifier = Modifier.padding(start = 48.dp)
        )
    }
}

@Composable
fun RegisterBody(viewModel: RegisterViewModel) {
    val email = viewModel.emailInput.collectAsState()
    val username = viewModel.usernameInput.collectAsState()
    val password = viewModel.passwordInput.collectAsState()
    val confirmPassword = viewModel.confirmPasswordInput.collectAsState()
    Column(modifier = Modifier
        .padding(horizontal = 48.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        TextField(value = username.value,
            placeholder = { Text(text = "username")},
            onValueChange = viewModel::setUsernameInput,
            colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = OrangeSecondary, backgroundColor = OrangeBackground),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = email.value,
            placeholder = { Text(text = "email")},
            onValueChange = viewModel::setEmailInput,
            colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = OrangeSecondary, backgroundColor = OrangeBackground),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = password.value,
            placeholder = { Text(text = "password")},
            colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = OrangeSecondary, backgroundColor = OrangeBackground),
            onValueChange = viewModel::setPasswordInput,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = confirmPassword.value,
            placeholder = { Text(text = "confirm password")},
            colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = OrangeSecondary, backgroundColor = OrangeBackground),
            onValueChange = viewModel::setConfirmPasswordInput,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun RegisterButtonAction(onClickRegister:() -> Unit){
    Spacer(modifier = Modifier.height(32.dp))
    Button(modifier = Modifier
        .padding(horizontal = 48.dp)
        .fillMaxWidth()
        .height(48.dp),
        onClick = onClickRegister,
        colors = ButtonDefaults.buttonColors(backgroundColor = OrangeMain,
            contentColor = Color.White)
    ) {
        Text(text = "REGISTER", fontSize = 18.sp)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterPreview() {
    MyFirstComposeTheme {
        Column {
            RegisterHeader()
//            RegisterBody()
            RegisterButtonAction{}
        }
    }
}