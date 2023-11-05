package com.ironmeddie.donat.domain

import android.util.Log
import com.ironmeddie.donat.data.database.AppDatabase
import com.ironmeddie.donat.data.database.entity.toEntity
import com.ironmeddie.donat.data.database.entity.toTransactionPayload
import com.ironmeddie.donat.data.firestoreDb.RemoteDataBase
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SyncDataUseCase @Inject constructor(
    private val db: AppDatabase,
    private val remoteDB: RemoteDataBase
) {

    operator fun invoke() =
        remoteDB.getCategoryes().flatMapLatest { categories ->
            Log.d("checkCode","test categories")
            db.categoryDao().insertAll(categories.map { it.toEntity() })
        remoteDB.getCurrentMoney().flatMapLatest { money ->
            Log.d("checkCode","test money")
            db.currentMoneyDao().insert(money.toEntity())
            remoteDB.getTransactions().flatMapLatest { transactions ->
                Log.d("checkCode","test transactons")
                db.transactionDao().deleteAll()
                db.transactionDao().insertAll(transactions.map { transaction ->
                    db.transactionPayloadDao()
                        .addAll(transaction.categories.map { it.toTransactionPayload(transaction.id) })
                    transaction.toEntity()
                })
                flow { emit(SyncResult.Success) }
            }
        }
    }
//        remoteDB.getCategoryes().combine(remoteDB.getCurrentMoney()) { categories, money ->
//            Pair(categories, money)
//        }.combine(remoteDB.getTransactions()) { pair, transactions ->
//            val categories = pair.first
//            val money = pair.second
//
//
//        }


}

sealed class SyncResult {
    object Success : SyncResult()
    data class Failure(val message: String) : SyncResult()
}


