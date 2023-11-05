package com.ironmeddie.donat.domain.getMainScreenData

import com.ironmeddie.donat.data.database.AppDatabase
import com.ironmeddie.donat.data.firestoreDb.RemoteDataBase
import com.ironmeddie.donat.models.Money
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class getCurrentmoney @Inject constructor(val database: AppDatabase) {

    operator fun invoke() = database.currentMoneyDao().getAll().map { Money(money = it.allMoney.toDouble(), approved = it.approved.toDouble()) }
}