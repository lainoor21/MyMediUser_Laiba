package com.fuchsia.mymedicaluser.presentation.screens

import android.content.Context
import android.content.SharedPreferences
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fuchsia.mymedicaluser.R
import com.fuchsia.mymedicaluser.presentation.nav.Routes
import com.fuchsia.mymedicaluser.viewmodel.MyViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)

    val state = viewModel.getSpecificUserState.collectAsState()

    LaunchedEffect(Unit) {
        loadUserId(sharedPreferences)?.let {
            viewModel.getSpecificUser(it)
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
            val data = state.value.data!!

            val userDetails = listOf(
                "Name:" to data.name,
                "Email:" to data.email,
                "Phone Number:" to data.phone_number,
                "Address:" to data.address,
                "Password:" to data.password,
                "Pin Code:" to data.pincode,
                "Account Created on:" to data.date_of_account_creation,
                "Status:" to if (data.isApproved == 1) "Approved" else if (data.isApproved == 0) "Approval Pending" else "Blocked",
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.shutdownicon),
                        contentDescription = "LogOut",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                clearUserId(sharedPreferences)
                                (context as? ComponentActivity)?.finish()
                            }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(modifier.height(450.dp)) {

                    Box(
                        modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile Icon",
                            tint = Color(0xFF03727C),
                            modifier = Modifier.size(120.dp)
                        )

                    }
                    Box(
                        modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Hello ${data.name}",
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(userDetails) { detail ->
                            ItemView(label = detail.first, value = detail.second)
                        }
                    }

                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03727C)),

                    onClick = { navController.navigate(Routes.EditUserScreen) }) {
                    Text(text = "Edit Profile",
                        fontFamily = FontFamily.Serif)
                }
            }
        }
    }
}

@Composable
fun ItemView(modifier: Modifier = Modifier, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontFamily = FontFamily.Serif,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            fontFamily = FontFamily.Serif
        )
    }
}


fun loadUserId(sharedPreferences: SharedPreferences): String? {
    return sharedPreferences.getString("user_id", null)
}

fun clearUserId(sharedPreferences: SharedPreferences) {
    val editor = sharedPreferences.edit()
    editor.clear()
    editor.apply()
}