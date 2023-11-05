package com.ironmeddie.donat.ui.navHost

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.ironmeddie.donat.ui.Screens

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    this.navigate(Screens.LogIn, navOptions)
}

fun NavController.navigateToMainScreen(
    navOptions: NavOptions? = NavOptions.Builder().setPopUpTo(this.graph.id, true, false)
        .setLaunchSingleTop(true).build()
) {
    this.navigate(Screens.MainGraph, navOptions)
}

fun NavController.navigateToLoginScreen(navOptions: NavOptions? = NavOptions.Builder().setPopUpTo(this.graph.id, true, false)
    .setLaunchSingleTop(true).build()) {
    this.navigate(Screens.LoginGraph, navOptions)
}

fun NavController.navigateToProfile(){
    this.navigate(Screens.ProfileScreen)
}