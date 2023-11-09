package com.ironmeddie.donat.ui.navHost

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ironmeddie.donat.ui.Screens
import com.ironmeddie.donat.ui.loginScreen.SignInScreen
import com.ironmeddie.donat.ui.loginScreen.SignUpScreen
import com.ironmeddie.donat.ui.mainScrreen.MainScreen
import com.ironmeddie.donat.ui.profile.ProfileScreen
import com.ironmeddie.donat.ui.splash.SplashScreen

@Composable
fun MainNavHost(navController: NavHostController, context: Context) {
    NavHost(navController = navController, startDestination = Screens.SplashNav) {

        navigation(startDestination = Screens.SignIn, route = Screens.LoginGraph) {
            composable(Screens.SignIn) {
                SignInScreen(navController = navController)
            }
            composable(Screens.LogIn) {
                SignUpScreen(navController = navController)
            }
        }
        navigation(startDestination = Screens.MAIN_SCREEN, route = Screens.MainGraph) {
            composable(route = Screens.MAIN_SCREEN) {
                MainScreen(navController, context)
            }
            composable(route = Screens.ProfileScreen) {
                ProfileScreen(navController = navController)
            }
        }

        navigation(startDestination = Screens.SplashScreen, route = Screens.SplashNav) {
            composable(route = Screens.SplashScreen) {
                SplashScreen(navController)
            }
        }
    }

}
