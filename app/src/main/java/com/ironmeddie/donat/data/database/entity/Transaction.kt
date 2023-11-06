package com.ironmeddie.donat.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ironmeddie.donat.models.Transaction
import com.ironmeddie.donat.utils.toListOfStrings


@Entity
data class Transaction(
    @ColumnInfo(name = "userName")val userName: String ="",
    @ColumnInfo(name = "time")val time: String = "",
    @ColumnInfo(name = "money")val money: String = "",
    @ColumnInfo(name = "categories")val categories: String = "",
    @PrimaryKey val id : String = ""
){
    fun toModel()= Transaction(
        userName = this.userName,
        dateTime = this.time,
        money = this.money,
        id = this.id,
        categories = this.categories.toListOfStrings()
    )
}

fun Transaction.toEntity() = com.ironmeddie.donat.data.database.entity.Transaction(
    userName = userName,
    time = dateTime,
    money = money,
    id = id,
    categories = categories.toString()
)

