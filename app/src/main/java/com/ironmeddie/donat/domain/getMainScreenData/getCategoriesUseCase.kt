package com.ironmeddie.donat.domain.getMainScreenData

import com.ironmeddie.donat.data.database.AppDatabase
import com.ironmeddie.donat.data.firestoreDb.RemoteDataBase
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class getCategoriesUseCase @Inject constructor(private val database: AppDatabase) {

    operator fun invoke() =
        database.categoryDao().getAll().map { it.map { it.toCategory() } }
}