package com.fuchsia.mymedicaluser.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.fuchsia.mymedicaluser.viewmodel.MyViewModel
import com.fuchsia.mymedicaluser.presentation.nav.Routes

@Composable
fun SignUpScreen(navController: NavController, viewModel: MyViewModel = hiltViewModel()) {

    val state = viewModel.createUserState.collectAsState()

    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val pincode = remember { mutableStateOf("") }
    val phone_number = remember { mutableStateOf("") }

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
                        navController.navigate(Routes.Waiting(id = it.message))
                        viewModel.resetCreateUserState()
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

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Welcome to Medi Hub",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color(0xFF03727C)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Sign Up",
            color = Color(0xFF03727C),
            fontFamily = FontFamily.Serif,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = name.value,
            onValueChange = {
                name.value = it
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
                    text = "Name",
                    fontFamily = FontFamily.Serif,
                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
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
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = address.value,
            onValueChange = {
                address.value = it
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
                    text = "Address",
                    fontFamily = FontFamily.Serif,
                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = pincode.value,
            onValueChange = {
                pincode.value = it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
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
                    text = "Pincode",
                    fontFamily = FontFamily.Serif,
                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = phone_number.value,
            onValueChange = {
                phone_number.value = it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
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
                    text = "Phone Number",
                    fontFamily = FontFamily.Serif,
                )
            }
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(modifier = Modifier.width(200.dp), onClick = {

            if (name.value.isEmpty() || email.value.isEmpty()
                || password.value.isEmpty() || address.value.isEmpty()
                || pincode.value.isEmpty() || phone_number.value.isEmpty()
            ) {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.createUser(
                    name = name.value,
                    email = email.value,
                    password = password.value,
                    address = address.value,
                    pincode = pincode.value,
                    phone_number = phone_number.value
                )
            }
        }) {
            Text(
                text = "Sign Up",
                fontFamily = FontFamily.Serif,
            )
        }

    }

}

