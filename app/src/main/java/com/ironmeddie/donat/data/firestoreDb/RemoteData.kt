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
                    NodesDocumetsFields.FIELD_NAME to it.name,
                    NodesDocumetsFields.FIELD_PICTURE to "",
                    NodesDocumetsFields.FIELD_AMOUNT to "",
                    NodesDocumetsFields.FIELD_DESCRIPTION to "",
                    NodesDocumetsFields.FIELD_VOUTES to ""
                )
                db.collection(NodesDocumetsFields.NODE_CATEGORY)
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
                NodesDocumetsFields.FIELD_EMAIL to user.email,
                NodesDocumetsFields.FIELD_FIRSTNAME to user.firstName,
                NodesDocumetsFields.FIELD_DATETIME to FieldValue.serverTimestamp(),
                NodesDocumetsFields.FIELD_MONEY to purchase,
                NodesDocumetsFields.FIELD_CATEGORIES to categories.map { it.name }.toString()
            )
            db.collection(NodesDocumetsFields.NODE_TRANSACTIONS)
                .add(transaction)
                .await()

            db.collection(NodesDocumetsFields.NODE_MONEY).document(NodesDocumetsFields.DOCUMENT_MONEY).update(NodesDocumetsFields.FIELD_MONEY, FieldValue.increment(purchase))
                .await()

            val amount = if (categories.size>0)purchase/categories.size else purchase

            categories.forEach {
                db.collection(NodesDocumetsFields.NODE_CATEGORY).document(it.id).update(NodesDocumetsFields.FIELD_AMOUNT, FieldValue.increment(amount))
                    .await()

                db.collection(NodesDocumetsFields.NODE_CATEGORY).document(it.id).update(NodesDocumetsFields.FIELD_VOUTES, FieldValue.increment(1))
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
            val money = db.collection(NodesDocumetsFields.NODE_MONEY)
                .document(NodesDocumetsFields.DOCUMENT_MONEY)
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
                    .collection(NodesDocumetsFields.NODE_CATEGORY)
                    .get()
                    .await().map {
                        Category(
                            id = it.id,
                            name = it.data[NodesDocumetsFields.FIELD_NAME].toString(),
                            picture = it.data[NodesDocumetsFields.FIELD_PICTURE].toString(),
                            amount = it.data[NodesDocumetsFields.FIELD_AMOUNT].toString().toFloat(),
                            voutes = it.data[NodesDocumetsFields.FIELD_VOUTES].toString()
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
                    .collection(NodesDocumetsFields.NODE_TRANSACTIONS)
                    .get()
                    .await().map {

                        val cat = it.data[NodesDocumetsFields.FIELD_CATEGORIES].toString().toListOfStrings()
                        Transaction(
                            userName = it.data[NodesDocumetsFields.FIELD_FIRSTNAME].toString(),
                            email = it.data[NodesDocumetsFields.FIELD_EMAIL].toString(),
                            dateTime = (it.data[NodesDocumetsFields.FIELD_DATETIME] as Timestamp).toTimeFormat(),
                            money = it.data[NodesDocumetsFields.FIELD_MONEY].toString(),
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

object NodesDocumetsFields{

    // nodes
    const val NODE_CATEGORY = "alc_categories"
    const val NODE_TRANSACTIONS = "transactions"
    const val NODE_MONEY = "money"

    //documents
    const val DOCUMENT_MONEY = "money"

    // fields
    const val FIELD_MONEY = "money"
//    const val FIELD_APPROVED = "approved"


    const val FIELD_CATEGORIES = "categories"
    const val FIELD_DATETIME = "dateTime"
    const val FIELD_EMAIL = "email"
    const val FIELD_FIRSTNAME = "firstName"

    const val FIELD_AMOUNT = "amount"
    const val FIELD_VOUTES = "voutes"
    const val FIELD_PICTURE = "picture"
    const val FIELD_DESCRIPTION = "description"
    const val FIELD_NAME = "name"



}