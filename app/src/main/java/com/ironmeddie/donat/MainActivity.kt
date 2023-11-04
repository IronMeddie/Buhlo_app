package com.ironmeddie.donat

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ironmeddie.donat.domain.SyncDataUseCase
import com.ironmeddie.donat.ui.navHost.MainNavHost
import com.ironmeddie.donat.ui.theme.DonatTheme
import com.ironmeddie.donat.utils.Constance
import com.ironmeddie.donat.utils.activity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sync: SyncDataUseCase
    override fun onCreate(savedInstanceState: Bundle?) {
        activity = this
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            sync.invoke().collectLatest {
                Log.d(Constance.TAG, "sync")
            }
        }
        setContent {
            DonatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                   MainNavHost(navController = navController, this)
                }
            }
        }
    }
}


