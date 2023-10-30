package com.ironmeddie.donat

import android.app.Application
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig

class BApp: Application() {

    override fun onCreate() {
        super.onCreate()
        val config = AppMetricaConfig.newConfigBuilder("f1a1422e-b619-426e-a860-6b76caa20c18").build()
        // Initializing the AppMetrica SDK.
        AppMetrica.activate(this, config)

        AppMetrica.enableActivityAutoTracking(this)
//
    }
}