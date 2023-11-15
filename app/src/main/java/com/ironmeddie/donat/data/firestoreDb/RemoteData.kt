package com.ironmeddie.donat.data.firestoreDb

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.ironmeddie.donat.models.Category
import com.ironmeddie.donat.models.Money
import com.ironmeddie.donat.models.Transaction
import com.ironmeddie.donat.models.User
import com.ironmeddie.donat.ui.mainScrreen.components.getCategorys
import com.ironmeddie.donat.utils.Constance
import com.ironmeddie.donat.utils.toListOfStrings
import com.ironmeddie.donat.utils.toTimeFormat
import io.appmetrica.analytics.AppMetrica
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class RemoteData : RemoteDataBase {

    private val db = Firebase.firestore
    override suspend fun addCategory() {
        try {

            getCategorys().forEach {
                val user = hashMapOf(
                    "name" to it.name,
                    "picture" to "",
                    "amount" to "",
                    "description" to "",
                    "voutes" to ""
                )
                db.collection("alc_categories")
                    .add(user)
                    .await()

            }
        } catch (t: Throwable) {
            Log.d(Constance.TAG, t.message.toString())
            AppMetrica.reportError("firebase", t)
        }
    }

    override suspend fun addTransaction(user: User, purchase: Double, categories: List<Category>) {
        try {
            val transaction = hashMapOf(
                "email" to user.email,
                "firstName" to user.firstName,
                "dateTime" to FieldValue.serverTimestamp(),
                "money" to purchase,
                "categories" to categories.map { it.name }.toString()
            )
            db.collection("transactions")
                .add(transaction)
                .await()

            db.collection("money").document("money").update("money", FieldValue.increment(purchase))
                .await()

            val amount = if (categories.size>0)purchase/categories.size else purchase

            categories.forEach {
                db.collection("alc_categories").document(it.id).update("amount", FieldValue.increment(amount))
                    .await()

                db.collection("alc_categories").document(it.id).update("voutes", FieldValue.increment(1))
                    .await()
            }



            val event: HashMap<String, Any> = hashMapOf(
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
                val list: List<Category> =db
                    .collection("alc_categories")
                    .get()
                    .await().map {
                        Category(
                            id = it.id,
                            name = it.data["name"].toString(),
                            picture = it.data["picture"].toString(),
                            amount = it.data["amount"].toString().toFloat(),
                            voutes = it.data["voutes"].toString()
                        )
                    }
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

                        val cat = it.data["categories"].toString().toListOfStrings()
                        Transaction(
                            userName = it.data["firstName"].toString(),
                            email = it.data["email"].toString(),
                            dateTime = (it.data["dateTime"] as Timestamp).toTimeFormat(),
                            money = it.data["money"].toString(),
                            categories = cat,
                            id = it.id,
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