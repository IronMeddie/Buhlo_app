package com.ironmeddie.donat.data.auth

import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ironmeddie.donat.models.User
import com.ironmeddie.donat.utils.Constance
import com.ironmeddie.donat.utils.activity
import io.appmetrica.analytics.AppMetrica
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class Auth() : Authorization {

    private val auth = Firebase.auth
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private val db = Firebase.firestore



        override fun getCurrent() = flow<FirebaseUser?> {
            val currentUser = auth.getCurrentUser()

            emit(currentUser)
        }



    fun request(){
//        oneTapClient = Identity.getSignInClient(activity)
//        signInRequest = BeginSignInRequest.builder()
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    .setServerClientId("560462816674-m3ro17fps00cliie83gs5sgh1v81gli0.apps.googleusercontent.com")
//                    .setFilterByAuthorizedAccounts(true)
//                    .build())
//            .build()


    }

    fun token(intent: Intent?){
        val googleCredential = oneTapClient.getSignInCredentialFromIntent(intent)
        val idToken = googleCredential.googleIdToken
        when {
            idToken != null -> {
                // Got an ID token from Google. Use it to authenticate
                // with Firebase.
                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(firebaseCredential)
//                    .addOnCompleteListener(activity) { task ->
//                        if (task.isSuccessful) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(Constance.TAG, "signInWithCredential:success")
//                            val user = auth.currentUser
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(Constance.TAG, "signInWithCredential:failure", task.exception)
//                        }
//                    }
            }
            else -> {
                // Shouldn't happen.
                Log.d(Constance.TAG, "No ID token!")
            }
        }
    }
 
    fun beginSignIn(){
//        oneTapClient.beginSignIn(signInRequest)
//            .addOnSuccessListener(activity) { result ->
//                try {
////                    startIntentSenderForResult(
////                        result.pendingIntent.intentSender, REQ_ONE_TAP,
////                        null, 0, 0, 0, null)
//                } catch (e: IntentSender.SendIntentException) {
//                    Log.e(Constance.TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
//                }
//            }
//            .addOnFailureListener(activity) { e ->
//                // No saved credentials found. Launch the One Tap sign-up flow, or
//                // do nothing and continue presenting the signed-out UI.
//                Log.d(Constance.TAG, e.localizedMessage)
//            }

    }

    override fun logIn(login: String, password: String): Flow<AuthResult> = flow {
    try {
        auth.signInWithEmailAndPassword(login, password).await()
        emit(AuthResult.Success)
    }catch (t: Throwable){
        AppMetrica.reportError("logIn",t)
        emit(AuthResult.Failure(t.message.toString()))
    }

    }

    override fun logOut(): Flow<AuthResult> = flow {
        try {
            auth.signOut()
            emit(AuthResult.Success)
        }catch (t: Throwable){
            AppMetrica.reportError("logOut",t)
            emit(AuthResult.Failure(t.message.toString()))
        }
    }

    override fun registration(login: String, password: String, user: User) = flow<AuthResult> {

        try {
            auth.createUserWithEmailAndPassword(login, password).await()
            emit(AuthResult.Success)
        }catch (e: Throwable){

            emit(AuthResult.Failure(e.message.toString()))
            AppMetrica.reportError("registration", e)
        }
    }
}

sealed class AuthResult{
    object Success: AuthResult()
    object Loading: AuthResult()
//    object logInSuccess: AuthResult()
//    object regSuccess: AuthResult()
    data class Failure(val message: String): AuthResult()
}