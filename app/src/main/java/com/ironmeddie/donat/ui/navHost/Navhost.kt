package com.ironmeddie.donat.ui.navHost

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ironmeddie.donat.ui.BuhloList.BuhloList
import com.ironmeddie.donat.ui.Screens
import com.ironmeddie.donat.ui.mainScrreen.MainScreen

@Composable
fun MainNavHost(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screens.MAIN_SCREEN){
        composable(route = Screens.MAIN_SCREEN){
            MainScreen(navController)
        }
        composable(route = Screens.BUHLO_LIST){
            BuhloList()
        }
    }

}

fun NavController.navigateToListOfBuhlo(){
    this.navigate(route = Screens.BUHLO_LIST)
}