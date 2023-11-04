package com.ironmeddie.donat.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ironmeddie.donat.models.Category

@Entity
data class TransactionPayload(
    val category: String ="",
    val transactionID: String = "",
    val payload: String = "",
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

fun Category.toTransactionPayload(id: String) : TransactionPayload{
    return TransactionPayload(
        category = name,
        transactionID = id
    )

}

