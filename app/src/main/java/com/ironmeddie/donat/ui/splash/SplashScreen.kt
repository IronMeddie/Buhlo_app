package com.ironmeddie.donat.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ironmeddie.donat.data.auth.AuthResult
import com.ironmeddie.donat.domain.SyncResult
import com.ironmeddie.donat.ui.navHost.navigateToLoginScreen
import com.ironmeddie.donat.ui.navHost.navigateToMainScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(navController: NavHostController, viewModel: SplashViewModel = hiltViewModel()) {
    val user = viewModel.user.collectAsState(initial = null).value

    val sync = viewModel.state

    LaunchedEffect(user) {
        when (user) {
            is AuthResult.Success -> {
                viewModel.syncData()
            }

            is AuthResult.Failure -> {
                navController.navigateToLoginScreen()
            }

            else -> Unit
        }
    }

    LaunchedEffect(true) {
        sync.collectLatest {
            navController.navigateToMainScreen()
        }
//        when (sync) {
//            is SyncResult -> {
//                navController.navigateToMainScreen()
//            }
//            else -> Unit
//        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {

        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onBackground)
                .align(Alignment.Center)
        ) {

        }
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = "Buhlo",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "App",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 35.dp, top = 16.dp)
            )
        }


    }
}