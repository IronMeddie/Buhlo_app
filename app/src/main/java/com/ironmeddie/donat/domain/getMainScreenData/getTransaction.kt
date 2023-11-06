package com.ironmeddie.donat.domain.getMainScreenData

import com.ironmeddie.donat.data.database.AppDatabase
import com.ironmeddie.donat.models.Transaction
import com.ironmeddie.donat.utils.toListOfStrings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class getTransaction @Inject constructor(private val db: AppDatabase) {

    operator fun invoke(): Flow<List<Transaction>> =  db.transactionDao().getTransactions().map{
        it.map {  transaction->
            transaction.toModel()
            }.sortedByDescending { it.dateTime }
       }
}