package com.ironmeddie.donat

import android.app.Application
import com.ironmeddie.donat.utils.Secret
import dagger.hilt.android.HiltAndroidApp
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig

@HiltAndroidApp
class BApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val config = AppMetricaConfig.newConfigBuilder(Secret.API_KEY)

            .build()
        AppMetrica.activate(this, config)
        AppMetrica.enableActivityAutoTracking(this)
        AppMetrica.sendEventsBuffer()

    }
}
