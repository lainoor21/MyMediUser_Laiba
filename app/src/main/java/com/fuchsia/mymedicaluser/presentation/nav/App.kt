package com.fuchsia.mymedicaluser.presentation.nav

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.fuchsia.mymedicaluser.presentation.screens.BottomNavBar
import com.fuchsia.mymedicaluser.presentation.screens.CreateOrderScreen
import com.fuchsia.mymedicaluser.presentation.screens.EditUserScreen
import com.fuchsia.mymedicaluser.presentation.screens.HomeScreen
import com.fuchsia.mymedicaluser.presentation.screens.LogInScreen
import com.fuchsia.mymedicaluser.presentation.screens.OrderHistoryScreen
import com.fuchsia.mymedicaluser.presentation.screens.SignUpScreen
import com.fuchsia.mymedicaluser.presentation.screens.WaitingScreen


@Composable
fun App() {

    val context = LocalContext.current
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)
    
    val navController = rememberNavController()

    val startDestination = if (sharedPreferences.loadUserId() != null) {
        Routes.BottomNavBar
    } else {
        Routes.LogIn
    }
    
    NavHost(navController = navController, startDestination = startDestination) {

        composable<Routes.BottomNavBar> {
            BottomNavBar(navController = navController)
        }

        composable<Routes.Signup> {
            SignUpScreen(navController)
        }

        composable<Routes.LogIn> {
            LogInScreen(navController)
        }

        composable<Routes.Waiting> {
            val data = it.toRoute<Routes.Waiting>()
            WaitingScreen(id = data.id, navController = navController)
        }

        composable<Routes.Home> {
            HomeScreen(navController = navController)

        }
        composable<Routes.EditUserScreen> {
            EditUserScreen(navController = navController)
        }
        composable<Routes.CreateOrderScreen> {
            CreateOrderScreen(
                navController = navController,
                productId = it.toRoute<Routes.CreateOrderScreen>().productId,
                productName = it.toRoute<Routes.CreateOrderScreen>().productName,
                category = it.toRoute<Routes.CreateOrderScreen>().category,
                price = it.toRoute<Routes.CreateOrderScreen>().price,
                stock = it.toRoute<Routes.CreateOrderScreen>().stock

            )
        }

        composable<Routes.OrderHistory> {
            OrderHistoryScreen(navController)
        }


    }

}

fun SharedPreferences.loadUserId(): String? {
    return getString("user_id", null)
}
