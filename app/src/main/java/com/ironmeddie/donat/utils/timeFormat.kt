package com.ironmeddie.donat.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

fun Timestamp.toTimeFormat(): String {

    val date = this.toDate()
    val formatter = SimpleDateFormat("dd/MM/yy  HH:mm")
    val formattedDate: String = formatter.format(date)

    return formattedDate
}