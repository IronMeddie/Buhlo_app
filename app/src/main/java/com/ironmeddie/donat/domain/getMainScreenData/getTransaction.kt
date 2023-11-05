package com.ironmeddie.donat.domain.getMainScreenData

import com.ironmeddie.donat.data.database.AppDatabase
import com.ironmeddie.donat.models.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class getTransaction @Inject constructor(private val db: AppDatabase) {

    operator fun invoke(): Flow<List<Transaction>> =
        db.transactionDao().getTransactions().map { it.map { transaction ->
            Transaction(userName = transaction.userName,
                dateTime = transaction.time,
                money = transaction.money,
                categories = emptyList(),
                id= transaction.id

            ) }.sortedByDescending { it.dateTime } }




}