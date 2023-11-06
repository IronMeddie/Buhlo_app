package com.ironmeddie.donat.models

data class Transaction(
    val userName: String ="",
    val email: String = "",
    val dateTime: String = "",
    val money: String = "",
    val categories: List<String> = emptyList(),
    val id : String = ""
)