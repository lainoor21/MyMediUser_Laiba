package com.fuchsia.mymedicaluser.presentation.screens

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fuchsia.mymedicaluser.R
import com.jet.ads.admob.AdMobTestIds
import com.jet.ads.admob.banner.AdaptiveBanner

@Composable
fun BottomNavBar(modifier: Modifier = Modifier, navController: NavController) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    var backPressedOnce by remember { mutableStateOf(false) }
    val context = LocalContext.current

    BackHandler {
        if (backPressedOnce) {
            (context as? ComponentActivity)?.finish()
        } else {
            backPressedOnce = true
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        bottomBar = {

            Column {

                Box (modifier.wrapContentHeight()){

                    AdaptiveBanner(AdMobTestIds.ADAPTIVE_BANNER)
                }


                NavigationBar(modifier = Modifier.height(65.dp)) {
                    NavigationBarItem(
                        selected = selectedIndex == 0,
                        onClick = { selectedIndex = 0 },
                        label = {
                            Text(
                                text = "Home",
                                fontFamily = FontFamily.Serif,
                            )
                        },
                        icon = {
                            Icon(
                                Icons.Default.Home,
                                contentDescription = "Home"
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = colorResource(id = R.color.teal_700),
                            selectedTextColor = colorResource(id = R.color.teal_700),
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 1,
                        onClick = { selectedIndex = 1 },
                        label = {
                            Text(
                                text = "Products",
                                fontFamily = FontFamily.Serif,
                            )
                        },
                        icon = {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = "Products"
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = colorResource(id = R.color.teal_700),
                            selectedTextColor = colorResource(id = R.color.teal_700),
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    NavigationBarItem(
                        selected = selectedIndex == 2,
                        onClick = { selectedIndex = 2 },
                        label = {
                            Text(
                                text = "Order History",
                                fontFamily = FontFamily.Serif,
                            )
                        },
                        icon = {
                            Icon(
                                Icons.Default.History,
                                contentDescription = "Order History"
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = colorResource(id = R.color.teal_700),
                            selectedTextColor = colorResource(id = R.color.teal_700),
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedIndex) {
                0 -> DashboardScreen(navController = navController)
                1 -> ProductsScreen(navController = navController)
                2 -> OrderHistoryScreen(navController)
            }
        }
    }
}