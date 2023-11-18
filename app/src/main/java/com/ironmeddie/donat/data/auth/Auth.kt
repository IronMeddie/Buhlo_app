package com.ironmeddie.donat.data.auth

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.ironmeddie.donat.models.User
import io.appmetrica.analytics.AppMetrica
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class Auth() : Authorization {

    private val auth = Firebase.auth

    override fun getCurrent() = flow<FirebaseUser?> {
        val currentUser = auth.getCurrentUser()

        emit(currentUser)
    }

    override fun logIn(login: String, password: String): Flow<AuthResult> = flow {
        try {
            auth.signInWithEmailAndPassword(login, password).await()


            emit(AuthResult.Success)
        } catch (t: Throwable) {
            AppMetrica.reportError("logIn", t)
            emit(AuthResult.Failure(t.message.toString()))
        }

    }

    override fun changeAvater(photoUri: Uri): Flow<AuthResult> = flow {

        try {
            val firebaseUser = Firebase.auth.currentUser

            val profileUpdates = userProfileChangeRequest {
                this.photoUri = photoUri
            }

            firebaseUser!!.updateProfile(profileUpdates).await()
            emit(AuthResult.Success)
        } catch (t: Throwable) {
            AppMetrica.reportError("changePhoto", t)
            emit(AuthResult.Failure(t.message.toString()))
        }

    }

    override fun changeFirstName(name: String): Flow<AuthResult> = flow {
        try {
            val firebaseUser = Firebase.auth.currentUser

            val profileUpdates = userProfileChangeRequest {
                this.displayName = name
            }
            firebaseUser!!.updateProfile(profileUpdates).await()
            emit(AuthResult.Success)
        } catch (t: Throwable) {
            AppMetrica.reportError("changePhoto", t)
            emit(AuthResult.Failure(t.message.toString()))
        }
    }

    override fun logOut(): Flow<AuthResult> = flow {
        try {
            auth.signOut()
            emit(AuthResult.Success)
        } catch (t: Throwable) {
            AppMetrica.reportError("logOut", t)
            emit(AuthResult.Failure(t.message.toString()))
        }
    }

    override fun registration(login: String, password: String, user: User) = flow<AuthResult> {

        try {
            auth.createUserWithEmailAndPassword(login, password).await()
            val firebaseUser = Firebase.auth.currentUser
            val profileUpdates = userProfileChangeRequest {
                displayName = user.firstName
            }
            firebaseUser!!.updateProfile(profileUpdates).await()
            emit(AuthResult.Success)
        } catch (e: Throwable) {

            emit(AuthResult.Failure(e.message.toString()))
            AppMetrica.reportError("registration", e)
        }
    }
}

sealed class AuthResult {
    object Success : AuthResult()
    object Loading : AuthResult()

    data class Failure(val message: String) : AuthResult()
}