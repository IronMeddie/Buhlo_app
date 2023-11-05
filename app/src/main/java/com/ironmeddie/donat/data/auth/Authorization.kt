package com.ironmeddie.donat.data.auth

import com.google.firebase.auth.FirebaseUser
import com.ironmeddie.donat.models.User
import kotlinx.coroutines.flow.Flow

interface Authorization {

    var currentUser: FirebaseUser?

    fun logIn(login: String, password: String) : Flow<AuthResult>

    fun logOut() : Flow<AuthResult>

    fun registration(login: String, password: String, user: User): Flow<AuthResult>
}