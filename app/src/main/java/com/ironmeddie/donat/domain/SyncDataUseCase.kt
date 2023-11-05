package com.ironmeddie.donat.domain

import android.util.Log
import com.ironmeddie.donat.data.database.AppDatabase
import com.ironmeddie.donat.data.database.entity.toEntity
import com.ironmeddie.donat.data.database.entity.toTransactionPayload
import com.ironmeddie.donat.data.firestoreDb.RemoteDataBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SyncDataUseCase @Inject constructor(
    private val db: AppDatabase,
    private val remoteDB: RemoteDataBase
) {

    operator fun invoke(): Flow<SyncResult> =
        remoteDB.getCategoryes().flatMapLatest { categories ->
            Log.d("checkCode","test categories")
            db.categoryDao().insertAll(categories.map { it.toEntity() })
        remoteDB.getCurrentMoney().flatMapLatest { money ->
            Log.d("checkCode","test money")
            db.currentMoneyDao().insert(money.toEntity())
            remoteDB.getTransactions().flatMapLatest { transactions ->
                Log.d("checkCode","test transactons")
                db.transactionDao().insertAll(transactions.map { transaction ->
                    db.transactionPayloadDao()
                        .addAll(transaction.categories.map { it.toTransactionPayload(transaction.id) })
                    transaction.toEntity()
                })
                flow { emit(SyncResult.Success) }
            }
        }
    }
}

sealed class SyncResult {
    object Success : SyncResult()
    data class Failure(val message: String) : SyncResult()
}


