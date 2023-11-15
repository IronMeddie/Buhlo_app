package com.ironmeddie.donat.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.ironmeddie.donat.data.database.dao.CategoryDao
import com.ironmeddie.donat.data.database.dao.CurrentMoneyDao
import com.ironmeddie.donat.data.database.dao.TransactionDao
import com.ironmeddie.donat.data.database.entity.Category
import com.ironmeddie.donat.data.database.entity.CurrentMoney
import com.ironmeddie.donat.data.database.entity.Transaction

@Database(
    entities = [
        CurrentMoney::class,
        Category::class,
        Transaction::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currentMoneyDao(): CurrentMoneyDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao

}

//interface RoomDBBase{
//
//    fun <T>dbSafeCall(getDb: () -> Flow<T>): Flow<T> =
//        getDb().catch {
//            AppMetrica.reportError("db", it)
//        }
//}

