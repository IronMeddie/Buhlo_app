package com.ironmeddie.donat.models

data class Transaction(
    val userName: String ="",
    val userID: String = "",
    val dateTime: String = "",
    val money: String = "",
    val categories: List<Category> = emptyList(),
    val id : String = ""
)