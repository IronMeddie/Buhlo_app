package com.ironmeddie.donat.utils

import android.app.Activity
import com.ironmeddie.donat.MainActivity
import com.ironmeddie.donat.data.RemoteData
import com.ironmeddie.donat.data.auth.Auth
import com.ironmeddie.donat.domain.getMainScreenData.getCategoriesUseCase
import com.ironmeddie.donat.domain.getMainScreenData.getCurrentmoney
import com.ironmeddie.donat.domain.getMainScreenData.updateMoneyValue

var activity: Activity? = null
object di {

    fun returnDatabase() = RemoteData()

    fun returnAuth(): Auth {
         return Auth(activity = activity!!)
    }




    fun getMaiScreenUseCases() = getCategoriesUseCase(returnDatabase())

    fun getCurrentMoney() = getCurrentmoney(returnDatabase())

    fun updateMoneyUseCase() = updateMoneyValue(returnDatabase(), returnAuth())



}