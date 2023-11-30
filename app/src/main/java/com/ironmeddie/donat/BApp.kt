package com.ironmeddie.donat

import android.app.Application
import android.util.Log
import com.ironmeddie.donat.utils.Secret
import com.yandex.varioqub.appmetricaadapter.AppMetricaAdapter
import com.yandex.varioqub.config.FetchError
import com.yandex.varioqub.config.OnFetchCompleteListener
import com.yandex.varioqub.config.Varioqub
import com.yandex.varioqub.config.VarioqubSettings
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

        val settings = VarioqubSettings.Builder("appmetrica.4485178")
            .withLogs()
            .withThrottleInterval(10)
            .build()

        Varioqub.init(settings, AppMetricaAdapter(this), this)

        Varioqub.fetchConfig(object : OnFetchCompleteListener {
            override fun onSuccess() {
                Log.i("VARIOQUB ID", Varioqub.getId())
            }
            override fun onError(message: String, error: FetchError) {
                AppMetrica.reportError(message,error.name)
            }
        })


    }
}
