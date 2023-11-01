package com.ironmeddie.donat.data.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.core.app.ActivityCompat.startIntentSenderForResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ironmeddie.donat.utils.Constance

class Auth(private val activity: Activity) {

    private val auth = Firebase.auth
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    var currentUser = auth.getCurrentUser()

    fun request(){
        oneTapClient = Identity.getSignInClient(activity)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId("560462816674-m3ro17fps00cliie83gs5sgh1v81gli0.apps.googleusercontent.com")
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            .build()


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
                    .addOnCompleteListener(activity) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(Constance.TAG, "signInWithCredential:success")
                            val user = auth.currentUser
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(Constance.TAG, "signInWithCredential:failure", task.exception)
                        }
                    }
            }
            else -> {
                // Shouldn't happen.
                Log.d(Constance.TAG, "No ID token!")
            }
        }
    }

    fun beginSignIn(){
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(activity) { result ->
                try {
//                    startIntentSenderForResult(
//                        result.pendingIntent.intentSender, REQ_ONE_TAP,
//                        null, 0, 0, 0, null)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(Constance.TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(activity) { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                Log.d(Constance.TAG, e.localizedMessage)
            }

    }
}