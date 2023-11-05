package com.ironmeddie.donat.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ironmeddie.donat.models.Transaction


@Entity
data class Transaction(
    @ColumnInfo(name = "userName")val userName: String ="",
    @ColumnInfo(name = "time")val time: String = "",
    @ColumnInfo(name = "money")val money: String = "",
    @PrimaryKey val id : String = ""
)

fun Transaction.toEntity() = com.ironmeddie.donat.data.database.entity.Transaction(
    userName = userName,
    time = dateTime,
    money = money,
    id = id
)