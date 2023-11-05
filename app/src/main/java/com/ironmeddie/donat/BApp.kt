package com.ironmeddie.donat

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.ironmeddie.donat.data.database.AppDatabase
import com.ironmeddie.donat.domain.SyncDataUseCase
import com.ironmeddie.donat.utils.Constance
import dagger.hilt.android.HiltAndroidApp
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class BApp: Application() {



    override fun onCreate() {
        super.onCreate()
        val config = AppMetricaConfig.newConfigBuilder("f1a1422e-b619-426e-a860-6b76caa20c18").build()
        // Initializing the AppMetrica SDK.
        AppMetrica.activate(this, config)

        AppMetrica.enableActivityAutoTracking(this)
        AppMetrica.sendEventsBuffer()


    }
}