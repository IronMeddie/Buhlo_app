package com.ironmeddie.donat.utils

import com.google.firebase.Timestamp
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.ironmeddie.donat.data.firestoreDb.NodesDocumetsFields
import com.ironmeddie.donat.models.Transaction

fun String.toListOfStrings(): List<String> {
    val cat = this.substringAfter("[").substringBeforeLast("]").replace(" ", "")
    if (cat.contains(",")) return cat.split(",")
    else if (cat.isBlank()) return emptyList()
    else return listOf(cat)

}

fun QueryDocumentSnapshot.toTransaction(): Transaction{

        val cat = this.data[NodesDocumetsFields.FIELD_CATEGORIES].toString()
            .toListOfStrings()
       return Transaction(
            userName = this.data[NodesDocumetsFields.FIELD_FIRSTNAME].toString(),
            email = this.data[NodesDocumetsFields.FIELD_EMAIL].toString(),
            dateTime = (this.data[NodesDocumetsFields.FIELD_DATETIME] as Timestamp).toTimeFormat(),
            money = this.data[NodesDocumetsFields.FIELD_MONEY].toString(),
            categories = cat,
            id = this.id,
        )
}