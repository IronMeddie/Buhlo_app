package com.ironmeddie.donat.ui.profile

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseUser
import com.ironmeddie.donat.R
import com.ironmeddie.donat.data.auth.AuthResult
import com.ironmeddie.donat.ui.mainScrreen.components.MyTextField
import com.ironmeddie.donat.ui.navHost.navigateToLoginScreen
import com.ironmeddie.donat.ui.theme.Border
import com.ironmeddie.donat.ui.theme.GreyField
import com.ironmeddie.donat.ui.theme.GreyIconBack
import com.ironmeddie.donat.ui.theme.NameProfile
import com.ironmeddie.donat.ui.theme.OnotherOneGrey
import com.ironmeddie.donat.ui.theme.TransparentWhite
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val user: FirebaseUser? = viewModel.user.collectAsState().value
    val firstname = viewModel.firstName.collectAsState().value

    var result by remember {
        mutableStateOf("")
    }

    var isTextFieldNeeded by remember {
        mutableStateOf(false)
    }


    val signOut = viewModel.logOut.collectAsState().value
    LaunchedEffect(key1 = signOut) {
        if (signOut is AuthResult.Success) navController.navigateToLoginScreen()
    }

    LaunchedEffect(key1 = viewModel.uploadResult) {
        viewModel.uploadResult.collect {
            when (it) {
                is AuthResult.Failure -> {
                    result = it.message
                }

                is AuthResult.Success -> {
                    result = "Данные обновлены"
                    delay(2000)
                    result = ""
                }

                is AuthResult.Loading -> {
                    result = "загрузка"
                }
            }
        }
    }

    val context = LocalContext.current

    val contract =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(
                    uri ?: return@rememberLauncherForActivityResult, flag
                )

                scope.launch {
                    selectedImageUri = uri
                    viewModel.updateUser(uri)
                }
            })

    Box {


        Scaffold(modifier = Modifier.fillMaxSize()) {

            LazyColumn(modifier = Modifier.padding(it)) {
                item { ProfileTopBar() { navController.navigateUp() } }

                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = if (selectedImageUri == null) {
                                if (!user?.photoUrl?.scheme.isNullOrBlank()) user?.photoUrl else R.drawable.avatar
                            } else selectedImageUri, contentDescription = "user avatar",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(61.dp)
                                .border(1.dp, Border, CircleShape), contentScale = ContentScale.Crop
                        )
                        Text(
                            text = stringResource(R.string.change_photo),
                            fontWeight = FontWeight.W500,
                            fontSize = 8.sp,
                            color = OnotherOneGrey,
                            modifier = Modifier.clickable {
                                contract.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(17.dp))
                        Text(
                            text = user?.displayName ?: user?.email.orEmpty(),
                            style = MaterialTheme.typography.headlineSmall,
                            fontSize = 15.sp,
                            color = NameProfile
                        )
                    }
                }
//
                item {
                    Spacer(modifier = Modifier.height(36.dp))
                    Text(text = result)
                }

                item {
                    AnimatedVisibility(visible = isTextFieldNeeded) {
                            MyTextField(
                                value = firstname,
                                hint = stringResource(R.string.firstname),
                                onValueChange = viewModel::firstnameChange,
                                modifier = Modifier
                                    .padding(24.dp)
                                    .fillMaxWidth()
                                    .height(35.dp)
                                    .clip(MaterialTheme.shapes.medium)
                                    .shadow(12.dp, MaterialTheme.shapes.medium)
                                    .background(GreyField)
                            )

                    }


                }
                item {
                    AnimatedVisibility(visible = isTextFieldNeeded && firstname.isNotBlank()) {
                        Button(
                            onClick = {
                                viewModel.uploadNewFirstName()
                                result = "loading"
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                                .height(46.dp),
                            enabled = user?.displayName != firstname && firstname.isNotBlank()
                        ) {
                            Text(text = stringResource(R.string.change))
                        }
                    }
                }


                item {
                    ProfileListItem(
                        stringResource(R.string.change_username),
                        R.drawable.credit_card,
                        { isTextFieldNeeded = !isTextFieldNeeded }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_next),
                            contentDescription = "arrow"
                        )
                    }
                }

                item {
                    ProfileListItem(stringResource(R.string.log_out), R.drawable.log_in, {
                        viewModel.logOut()

                    }) {}
                }

            }
        }
        if (result == "loading")
        Box(Modifier.fillMaxSize().background(TransparentWhite), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ProfileTopBar(onClickBack: () -> Unit) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()

    ) {
        IconButton(onClick = onClickBack, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "icon back")
        }
        Text(
            text = stringResource(R.string.profile),
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 15.sp
        )
    }
}


@Composable
fun ProfileListItem(
    title: String,
    icon: Int,
    onClick: () -> Unit,
    endElement: @Composable () -> Unit,

    ) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp, vertical = 10.dp)
        .clickable { onClick() }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(GreyIconBack), contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "profile list item icon"
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = title, fontWeight = FontWeight.W500,
                fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground
            )

        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 13.dp)
        ) {
            endElement()
        }
    }
}