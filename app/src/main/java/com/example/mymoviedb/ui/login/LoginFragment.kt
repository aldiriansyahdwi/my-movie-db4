package com.example.mymoviedb.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.example.mymoviedb.R
import com.example.mymoviedb.ui.theme.MyFirstComposeTheme
import com.example.mymoviedb.ui.theme.OrangeBackground
import com.example.mymoviedb.ui.theme.OrangeMain
import com.example.mymoviedb.ui.theme.OrangeSecondary
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )
            setContent{
                alreadyLogin()
                MyFirstComposeTheme {
                    Column{
                        LoginHeader()
                        LoginBody(viewModel)
                        LoginButtonAction(onClickLogin = {
                            viewModel.apply {
                                verifyLogin(emailInput.value, passwordInput.value)
                                user.observe(viewLifecycleOwner){ user->
                                    if (user.isEmpty()){
                                        Toast.makeText(
                                            requireContext(), "account not found", Toast.LENGTH_SHORT).show()
                                    } else {
                                        saveDataStore(user[0].email, user[0].username.toString())
                                        Toast.makeText(requireContext(), "hello ${user[0].username}", Toast.LENGTH_SHORT).show()
                                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                                    }
                                }
                            }
                        }, onClickRegister = {
                            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                        })
                    }
                }
            }
        }
    }

    private fun alreadyLogin(){
        viewModel.getUsername().observe(viewLifecycleOwner) {
            if (it != "-") {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }
}

@Composable
fun LoginHeader(){
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.heightIn(min = 64.dp, max = 128.dp))
        Image(painterResource(id = R.drawable.ic_app_main),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier.widthIn(min = 64.dp, max = 192.dp),
        )
    }
}

@Composable
fun LoginBody(viewModel: LoginViewModel){
    val email = viewModel.emailInput.collectAsState()
    val password = viewModel.passwordInput.collectAsState()
    Column(modifier = Modifier
        .padding(horizontal = 48.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
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
    }
}

@Composable
fun LoginButtonAction(onClickLogin :() -> Unit, onClickRegister:() -> Unit){
    Column(modifier = Modifier
        .padding(horizontal = 48.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(32.dp))
        Button(modifier = Modifier.fillMaxWidth(), onClick = onClickLogin, colors = ButtonDefaults.buttonColors(backgroundColor = OrangeMain, contentColor = Color.White)) {
            Text(text = "LOGIN")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Not registered yet?", modifier = Modifier.clickable(onClick = onClickRegister), style = TextStyle(color = Color.Black) )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginPreview() {
    MyFirstComposeTheme {
        Column {
            LoginHeader()
            LoginButtonAction({}, {})
        }
    }
}