package com.fuchsia.mymedicaluser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.fuchsia.mymedicaluser.presentation.nav.App
import com.fuchsia.mymedicaluser.presentation.screens.SignUpScreen
import com.fuchsia.mymedicaluser.presentation.screens.WaitingScreen
import com.fuchsia.mymedicaluser.ui.theme.MyMedicalUserTheme
import com.jet.ads.admob.AdMobTestIds
import com.jet.ads.admob.banner.AdaptiveBanner
import com.jet.ads.common.initializers.AdsInitializeFactory
import com.jet.ads.common.initializers.AdsInitializer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), AdsInitializer by AdsInitializeFactory.admobInitializer() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        initializeAds()

        setContent {
            MyMedicalUserTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.systemBarsPadding()){
                        App()
                    }
                }
            }
        }
    }
}

