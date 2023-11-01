package com.ironmeddie.donat.models

data class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val avatar: String = "",
    val isAdmin: Boolean = false
)