package com.ironmeddie.donat.data.firestoreDb

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.ironmeddie.donat.models.Category
import com.ironmeddie.donat.models.Money
import com.ironmeddie.donat.models.Transaction
import com.ironmeddie.donat.models.User
import com.ironmeddie.donat.ui.mainScrreen.components.getCategorys
import com.ironmeddie.donat.utils.Constance
import io.appmetrica.analytics.AppMetrica
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class RemoteData: RemoteDataBase {

    private val db = Firebase.firestore
    override fun addCategory() {
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

    override suspend fun addTransaction(user: User, purchase: Double,categories: List<Category>) {
        try {
            val transaction = hashMapOf(
                "userID" to user.id,
                "dateTime" to FieldValue.serverTimestamp(),
                "money" to purchase,
                "categories" to categories.map { it.name }
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

            val event : HashMap<String,Any> = hashMapOf(
                "userID" to user.id,
                "revenue" to purchase
            )
            AppMetrica.reportEvent("transaction", event)

        } catch (t: Throwable) {
            Log.d(Constance.TAG, t.message.toString())
            AppMetrica.reportError("firebase", t)
        }
    }

    override fun getCurrentMoney(): Flow<Money> = flow {
        try {
            val money = db.collection("money")
                .document("money")
                .get()
                .await().toObject<Money>()
            emit(money ?: Money())

        } catch (t: Throwable) {
            Log.d(Constance.TAG, t.message.toString())
            AppMetrica.reportError("firebase", t)
        }
    }


    override fun getCategoryes(): Flow<List<Category>> {

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

    override fun getTransactions(): Flow<List<Transaction>> {
        return flow<List<Transaction>> {
            try {
                val list = db
                    .collection("transactions")
                    .get()
                    .await().map {

                        val cat= it.data["categories"]
                        Log.d("checkCode categories",cat.toString())
                    Transaction(
                        userName  = it.data["username"].toString(),
                        userID = it.data["userID"].toString(),
                        dateTime = (it.data["dateTime"] as Timestamp).toDate().toString(),
                        money = it.data["money"].toString(),
                        categories =  emptyList(),
                        id = it.id
                    )
                    }


                emit(list)
            } catch (t: Throwable) {
                Log.d(Constance.TAG, t.message.toString())
                AppMetrica.reportError("firebase", t)
            }

        }
    }
}