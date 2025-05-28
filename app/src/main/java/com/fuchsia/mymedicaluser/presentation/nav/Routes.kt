package com.fuchsia.mymedicaluser.presentation.nav

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    object Signup : Routes()

    @Serializable
    object LogIn : Routes()

    @Serializable
    data class Waiting (val id: String)

    @Serializable
    object Home: Routes()

    @Serializable
    object EditUserScreen: Routes()

    @Serializable
    object BottomNavBar: Routes()

    @Serializable
    object OrderHistory: Routes()

    @Serializable
    data class CreateOrderScreen (val productId: String, val productName:String, val category:String, val price:Float, val stock:Int)

}