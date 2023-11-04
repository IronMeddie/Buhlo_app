package com.ironmeddie.donat.domain.getMainScreenData

import com.ironmeddie.donat.data.auth.Authorization
import com.ironmeddie.donat.data.firestoreDb.RemoteDataBase
import com.ironmeddie.donat.models.Category
import com.ironmeddie.donat.models.User
import javax.inject.Inject

class updateMoneyValue @Inject constructor(private val database: RemoteDataBase, private val authData : Authorization) {

    suspend operator fun invoke(money: Double, categories: List<Category>){

        val user = User(
            id = authData.currentUser?.tenantId ?: "",
            firstName = authData.currentUser?.displayName ?: "",
            email = authData.currentUser?.email ?: ""
        )
        database.addTransaction(user,money,categories)
    }
}