package com.ironmeddie.donat.ui.navHost

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ironmeddie.donat.ui.Screens
import com.ironmeddie.donat.ui.loginScreen.SignInScreen
import com.ironmeddie.donat.ui.loginScreen.SignUpScreen
import com.ironmeddie.donat.ui.mainScrreen.MainScreen

@Composable
fun MainNavHost(navController: NavHostController, context: Context){
    NavHost(navController = navController, startDestination = Screens.MainGraph){

        navigation(startDestination = Screens.SignIn, route = Screens.LoginGraph) {
            composable(Screens.SignIn) {
                SignInScreen(navController = navController)
            }
            composable(Screens.LogIn) {
                SignUpScreen(navController = navController)
            }
        }
        navigation(startDestination = Screens.MAIN_SCREEN, route = Screens.MainGraph) {
            composable(route = Screens.MAIN_SCREEN){
                MainScreen(navController,context)
            }
        }
    }

}

fun NavController.navigateToListOfBuhlo(){
    this.navigate(route = Screens.BUHLO_LIST)
}