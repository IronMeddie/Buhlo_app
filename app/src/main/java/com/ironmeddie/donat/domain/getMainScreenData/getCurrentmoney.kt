package com.ironmeddie.donat.domain.getMainScreenData

import com.ironmeddie.donat.data.firestoreDb.RemoteDataBase
import javax.inject.Inject

class getCurrentmoney @Inject constructor(val database: RemoteDataBase) {

    operator fun invoke() = database.getCurrentMoney()
}