package com.ironmeddie.donat.domain.getMainScreenData

import com.ironmeddie.donat.models.User
import com.ironmeddie.donat.data.RemoteData
import com.ironmeddie.donat.data.auth.Auth

class updateMoneyValue(private val database: RemoteData, private val authData : Auth) {

    suspend operator fun invoke(money: Double){

        val user = User(
            id = authData.currentUser?.tenantId ?: "",
            firstName = authData.currentUser?.displayName ?: "",
            email = authData.currentUser?.email ?: ""
        )
        database.addTransaction(user,money)
    }
}