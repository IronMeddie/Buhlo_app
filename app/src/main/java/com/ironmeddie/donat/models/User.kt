package com.ironmeddie.donat.models

import android.net.Uri

data class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val avatar: Uri = Uri.parse(""),
    val isAdmin: Boolean = false
)