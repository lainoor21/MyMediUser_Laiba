package com.fuchsia.mymedicaluser.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fuchsia.mymedicaluser.presentation.nav.Routes
import com.fuchsia.mymedicaluser.viewmodel.MyViewModel

@Composable
fun LogInScreen(navController: NavController, viewModel: MyViewModel = hiltViewModel()) {

    val state = viewModel.logInState.collectAsState()

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current

    when {
        state.value.isLoading -> {
            AlertDialog(modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(50.dp),
                containerColor = Color.Transparent,

                onDismissRequest = { /* Prevent dismissal */ },
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                ),
                title = null,
                text = {
                    Box(
                        modifier = Modifier
                            .size(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.White,
                        )
                    }
                },
                confirmButton = {},
                dismissButton = {}
            )
        }

        state.value.error != null -> {

            Toast.makeText(context, state.value.error, Toast.LENGTH_SHORT).show()
        }

        state.value.data != null -> {

            if (state.value.data != null) {

                LaunchedEffect(state.value.data) {
                    state.value.data?.let {

                        if(state.value.data!!.user_id != null){
                            navController.navigate(Routes.Waiting(id = state.value.data!!.user_id.toString()))
                            viewModel.resetLogInState()
                        }else{
                            Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "Welcome to My Medi Hub",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color(0xFF03727C)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Log In",
            fontFamily = FontFamily.Serif,
            fontSize = 20.sp,
            color = Color(0xFF03727C)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            colors = androidx.compose.material3.TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color(0xFFEBF7FF),
                unfocusedContainerColor = Color(0xFFEBF7FF),
                focusedIndicatorColor = Color(0xFF004373),
            ),
            label = {
                Text(
                    text = "Email",
                    fontFamily = FontFamily.Serif,
                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))


        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
            },
            colors = androidx.compose.material3.TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color(0xFFEBF7FF),
                unfocusedContainerColor = Color(0xFFEBF7FF),
                focusedIndicatorColor = Color(0xFF004373),
            ),
            label = {
                Text(
                    text = "Password",
                    fontFamily = FontFamily.Serif,
                )
            }
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(modifier = Modifier.width(200.dp), onClick = {

            if (email.value.isEmpty() || password.value.isEmpty()) {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.logIn(email.value, password.value)

            }
        }) {
            Text(
                text = "Log In",
                fontFamily = FontFamily.Serif,
            )
        }
        Spacer(modifier = Modifier.height(35.dp))
        Text(text = "Haven't signed up? Sign Up",
            fontFamily = FontFamily.Serif,
            modifier = Modifier.clickable {
                navController.navigate(Routes.Signup)
            })

    }

}
