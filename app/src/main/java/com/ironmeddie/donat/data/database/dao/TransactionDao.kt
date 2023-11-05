package com.ironmeddie.donat.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ironmeddie.donat.data.database.entity.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM `Transaction`")
    fun getTransactions(): Flow<List<Transaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Transaction>)

    @Query("DELETE FROM `Transaction`")
    fun deleteAll()
}