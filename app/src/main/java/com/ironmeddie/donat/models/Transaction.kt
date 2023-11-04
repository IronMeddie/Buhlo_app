package com.ironmeddie.donat.models

data class Transaction(
    val userName: String ="",
    val userID: String = "",
    val time: String = "",
    val money: String = "",
    val categories: List<Category> = emptyList(),
    val id : String = ""
)