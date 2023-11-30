package com.ironmeddie.donat.data.firestoreDb

import com.ironmeddie.donat.domain.SyncResult
import com.ironmeddie.donat.models.Category
import com.ironmeddie.donat.models.Money
import com.ironmeddie.donat.models.Transaction
import com.ironmeddie.donat.models.User
import kotlinx.coroutines.flow.Flow

interface RemoteDataBase {

    suspend fun addCategory()

    suspend fun addTransaction(user: User, purchase: Double,categories: List<Category>)

    fun getCurrentMoney(): Flow<Money>

    fun getCategoryes():Flow<List<Category>>
    fun getTransactions():Flow<List<Transaction>>

    fun resetBalance(): Flow<SyncResult>
}