package com.ironmeddie.donat.domain

import com.ironmeddie.donat.data.database.AppDatabase
import com.ironmeddie.donat.data.database.entity.toEntity
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
            db.categoryDao().deleteAll()
            db.categoryDao().insertAll(categories.map { it.toEntity() })
            remoteDB.getCurrentMoney().flatMapLatest { money ->
                db.currentMoneyDao().insert(money.toEntity())
                remoteDB.getTransactions().flatMapLatest { transactions ->
                    db.transactionDao().insertAll(transactions.map { transaction ->
                        transaction.toEntity()
                    })
                    flow<SyncResult> { emit(SyncResult.Success) }
                }.catch {
                    emit(SyncResult.Failure(it.message.toString()))
                }
            }
        }
}

sealed class SyncResult {
    object Success : SyncResult()
    data class Failure(val message: String) : SyncResult()

    object Loading: SyncResult()
}


