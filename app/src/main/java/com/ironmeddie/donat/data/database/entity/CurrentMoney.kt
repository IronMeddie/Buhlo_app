package com.ironmeddie.donat.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ironmeddie.donat.data.firestoreDb.Money


@Entity
data class CurrentMoney(
    @PrimaryKey val uid: Int = 1,
    @ColumnInfo(name = "approved") val approved: String?,
    @ColumnInfo(name = "allMoney") val allMoney: String?,
    )

fun Money.toEntity()= CurrentMoney(
    approved = approved.toString(),
    allMoney = money.toString())
