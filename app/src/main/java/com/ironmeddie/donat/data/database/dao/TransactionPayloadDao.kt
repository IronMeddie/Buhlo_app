package com.ironmeddie.donat.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ironmeddie.donat.data.database.entity.TransactionPayload
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionPayloadDao {

    @Query("SELECT * FROM TransactionPayload WHERE transactionID = :transactionID")
    fun getTransactionCategories(transactionID: String): Flow<List<TransactionPayload>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(payload: List<TransactionPayload>)
}