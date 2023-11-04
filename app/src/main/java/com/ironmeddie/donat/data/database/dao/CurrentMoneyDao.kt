package com.ironmeddie.donat.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ironmeddie.donat.data.database.entity.CurrentMoney
import kotlinx.coroutines.flow.Flow


@Dao
interface CurrentMoneyDao {
    @Query("SELECT * FROM CurrentMoney WHERE uid = 1 LIMIT 1")
    fun getAll(): Flow<CurrentMoney>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(money: CurrentMoney)

    @Delete
    fun delete(money: CurrentMoney)
}