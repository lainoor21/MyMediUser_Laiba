package com.fuchsia.mymedicaluser.presentation.screens

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fuchsia.mymedicaluser.presentation.nav.Routes
import com.fuchsia.mymedicaluser.viewmodel.MyViewModel
import kotlinx.coroutines.delay

@Composable
fun WaitingScreen(
    navController: NavController,
    id: String? = null,
    viewModel: MyViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val state = viewModel.getSpecificUserState.collectAsState()
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)

    LaunchedEffect(Unit) {
        if (id != null) {
            viewModel.getSpecificUser(id)
        }
    }

    when {
        state.value.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()

            }

        }

        state.value.error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                Text(text = state.value.error!!)

            }

        }

        state.value.data != null -> {

            if (state.value.data!!.user_id != null) {


                state.value.data?.isApproved?.let { message ->

                    if (message == 1) {

                        saveUserId(sharedPreferences, state.value.data?.user_id.toString())
                        LaunchedEffect(Unit) {
                            delay(500)
                            navController.navigate(Routes.BottomNavBar)

                        }

                    } else if (message == 2) {

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center

                        ) {
                            Text(
                                text = "You are Blocked",
                                fontFamily = FontFamily.Serif
                            )

                        }

                    } else if(message == 0) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center

                        ) {

                            Text(
                                text = "Hello ${state.value.data?.name} Please Wait for Approval",
                                fontFamily = FontFamily.Serif
                            )

                        }

                    }

                } ?: run {
                    Text(text = "No message available")
                }
            }else{
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    Text(text = "Email already in use")
                }
            }

        }

    }

}

fun saveUserId(sharedPreferences: SharedPreferences, user_id: String) {
    val editor = sharedPreferences.edit()
    editor.putString("user_id", user_id)
    editor.apply()
}
