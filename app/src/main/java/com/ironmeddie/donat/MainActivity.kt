package com.ironmeddie.donat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ironmeddie.donat.data.database.AppDatabase
import com.ironmeddie.donat.domain.SyncDataUseCase
import com.ironmeddie.donat.ui.navHost.MainNavHost
import com.ironmeddie.donat.ui.theme.DonatTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

var AppDatabaseTest: AppDatabase? = null

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sync: SyncDataUseCase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        CoroutineScope(Dispatchers.IO).launch {
//            sync.invoke().collectLatest {
//                Log.d(Constance.TAG, "sync")
//            }
//        }
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


