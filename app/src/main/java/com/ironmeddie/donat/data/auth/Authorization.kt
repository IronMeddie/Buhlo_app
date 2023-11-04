package com.ironmeddie.donat.data.auth

import com.google.firebase.auth.FirebaseUser
import com.ironmeddie.donat.models.User

interface Authorization {

    var currentUser: FirebaseUser?

    fun logIn(login: String, password: String)

    fun logOut()

    fun registration(login: String, password: String, user: User)
}