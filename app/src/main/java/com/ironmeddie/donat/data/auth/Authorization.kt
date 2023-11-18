package com.ironmeddie.donat.data.auth

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.ironmeddie.donat.models.User
import kotlinx.coroutines.flow.Flow

interface Authorization {

    fun getCurrent(): Flow<FirebaseUser?>

    fun logIn(login: String, password: String) : Flow<AuthResult>
    fun changeAvater(photoUri: Uri) : Flow<AuthResult>
    fun changeFirstName(name: String) : Flow<AuthResult>

    fun logOut() : Flow<AuthResult>

    fun registration(login: String, password: String, user: User): Flow<AuthResult>
}