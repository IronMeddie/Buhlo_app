package com.ironmeddie.donat.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ironmeddie.donat.models.Money


@Entity
data class CurrentMoney(
    @PrimaryKey val uid: Int = 1,
    @ColumnInfo(name = "approved") val approved: String = "",
    @ColumnInfo(name = "allMoney") val allMoney: String = "",
    ){
//    fun toMoney() = Money(
//        money = allMoney?.toDouble() ?: 0.0,
//        approved = approved?.toDouble() ?: 0.0
//    )
}

fun Money.toEntity()= CurrentMoney(
    approved = approved.toString(),
    allMoney = money.toString())
