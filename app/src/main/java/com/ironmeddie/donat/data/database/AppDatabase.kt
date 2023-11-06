package com.ironmeddie.donat.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ironmeddie.donat.data.database.dao.CategoryDao
import com.ironmeddie.donat.data.database.dao.CurrentMoneyDao
import com.ironmeddie.donat.data.database.dao.TransactionDao
import com.ironmeddie.donat.data.database.dao.UserDao
import com.ironmeddie.donat.data.database.entity.Category
import com.ironmeddie.donat.data.database.entity.CurrentMoney
import com.ironmeddie.donat.data.database.entity.Transaction
import com.ironmeddie.donat.data.database.entity.User

@Database(
    entities = [User::class,
        CurrentMoney::class,
        Category::class,
        Transaction::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun currentMoneyDao(): CurrentMoneyDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
}