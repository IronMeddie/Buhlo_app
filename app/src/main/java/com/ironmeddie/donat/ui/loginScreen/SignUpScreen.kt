package com.ironmeddie.donat.ui.loginScreen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ironmeddie.donat.R
import com.ironmeddie.donat.ui.mainScrreen.components.MyTextField
import com.ironmeddie.donat.ui.navHost.navigateToMainScreen
import com.ironmeddie.donat.ui.theme.GreyField
import com.ironmeddie.donat.ui.theme.GreyText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(navController: NavController, viewModel : LogInViewModel = hiltViewModel()) {

    val firstName = viewModel.firstName.collectAsState().value
    val password = viewModel.password.collectAsState().value
    var isError by remember{ mutableStateOf("") }
    LaunchedEffect(key1 = true){
        viewModel.eventFLow.collectLatest { logged ->
            when(logged){
                is Logged.Success ->{
                    Log.d("checkCode",  "Logged successful, navigating to mainScreen")
                    navController.navigateToMainScreen()
                }
                is Logged.Failure->{
                    isError = logged.message
                    delay(2000)
                    isError = ""
                }
            }

        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 43.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.welcome_back), style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(60.dp))
            MyTextField(
                firstName, modifier = Modifier
                    .fillMaxWidth()
                    .height(29.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(GreyField), stringResource(R.string.first_name),
                onValueChange = viewModel::updateFirstName
            )
            Spacer(modifier = Modifier.height(35.dp))

            MyPasswordField(
                password, modifier = Modifier
                    .fillMaxWidth()
                    .height(29.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(GreyField), stringResource(R.string.password),
                viewModel::updatePassword
            )
            AnimatedVisibility(visible = isError.isNotEmpty()) {
               Text(text = isError)
            }

            Spacer(modifier = Modifier.height(99.dp))
            Button(
                onClick = {
                    viewModel.logIn()
                          }, modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
            ) {
                Text(text = stringResource(R.string.login))
            }
            Spacer(modifier = Modifier.height(205.dp))


        }
    }

}


@Composable
fun MyPasswordField(
    value: String,
    modifier: Modifier = Modifier,
    hint: String,
    onValueChange: (String) -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }


    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .matchParentSize()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
        if (value.isEmpty()) Text(
            text = hint,
            color = GreyText,
            modifier = Modifier.align(Alignment.Center)
        )
        Icon(
            painter = painterResource(id = if (isVisible) R.drawable.eye else R.drawable.eye_off),
            contentDescription = "password visibility icon",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 7.dp)
                .clickable {
                    isVisible = !isVisible
                }
                .padding(8.dp)
        )
    }
}