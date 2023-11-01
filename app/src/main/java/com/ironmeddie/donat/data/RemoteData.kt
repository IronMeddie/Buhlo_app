package com.ironmeddie.donat.data

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ironmeddie.donat.data.firestoreDb.Money
import com.ironmeddie.donat.models.Category
import com.ironmeddie.donat.models.User
import com.ironmeddie.donat.ui.mainScrreen.components.getCategorys
import com.ironmeddie.donat.utils.Constance
import io.appmetrica.analytics.AppMetrica
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class RemoteData {

    private val db = Firebase.firestore
    fun addCategory() {
        try {

            getCategorys().forEach {
                val user = hashMapOf(
                    "name" to it.name,
                    "picture" to "",
                    "id" to it.id
                )
                db.collection("alc_categories")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(
                            Constance.TAG,
                            "DocumentSnapshot added with ID: ${documentReference.id}"
                        )
                    }
                    .addOnFailureListener { e ->
                        Log.w(Constance.TAG, "Error adding document", e)
                        AppMetrica.reportError("firebase", e)
                    }
            }
        } catch (t: Throwable) {
            Log.d(Constance.TAG, t.message.toString())
            AppMetrica.reportError("firebase", t)
        }
    }

    suspend fun addTransaction(user: User, purchase: Double) {
        try {
            val transaction = hashMapOf(
                "userID" to user.id,
                "dateTime" to FieldValue.serverTimestamp(),
                "revenue" to purchase
            )
            db.collection("transactions")
                .add(transaction)
                .await()
//                    .addOnSuccessListener { documentReference ->
//                        Log.d(Constance.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//                    }
//                    .addOnFailureListener { e ->
//                        Log.w(Constance.TAG, "Error adding document", e)
//                        AppMetrica.reportError("firebase", e)
//                    }
            db.collection("money").document("money").update("money", FieldValue.increment(purchase))
                .await()

        } catch (t: Throwable) {
            Log.d(Constance.TAG, t.message.toString())
            AppMetrica.reportError("firebase", t)
        }
    }

    fun getCurrentMoney() = flow {
        try {
            val money = db.collection("money")
                .document("money")
                .get()
                .await()["money"]
            emit(money.toString())

        } catch (t: Throwable) {
            Log.d(Constance.TAG, t.message.toString())
            AppMetrica.reportError("firebase", t)
        }
    }


    fun getCategoryes(): Flow<List<Category>> {

        return flow<List<Category>> {
            try {
                val list: List<Category> = db
                    .collection("alc_categories")
                    .get()
                    .await().toObjects(Category::class.java)

                emit(list)
            } catch (t: Throwable) {
                Log.d(Constance.TAG, t.message.toString())
                AppMetrica.reportError("firebase", t)
            }

        }


    }
}