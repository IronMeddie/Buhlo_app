package com.ironmeddie.donat.domain

import com.ironmeddie.donat.data.firestoreDb.RemoteDataBase
import javax.inject.Inject

class ResetBalance @Inject constructor(private val db: RemoteDataBase) {
    operator fun invoke()= db.resetBalance()
}