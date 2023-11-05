package com.ironmeddie.donat.domain.getMainScreenData

import com.ironmeddie.donat.data.auth.Authorization
import com.ironmeddie.donat.data.firestoreDb.RemoteDataBase
import com.ironmeddie.donat.models.Category
import com.ironmeddie.donat.models.User
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class updateMoneyValue @Inject constructor(private val database: RemoteDataBase, private val authData : Authorization) {

    suspend operator fun invoke(money: Double, categories: List<Category>){

        authData.getCurrent().collectLatest {
            val user = User(
                firstName = it?.displayName ?: "",
                email = it?.email ?: ""
            )
            database.addTransaction(user,money,categories)
        }

    }
}