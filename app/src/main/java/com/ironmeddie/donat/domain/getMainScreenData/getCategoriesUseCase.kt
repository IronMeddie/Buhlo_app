package com.ironmeddie.donat.domain.getMainScreenData

import com.ironmeddie.donat.data.firestoreDb.RemoteDataBase
import javax.inject.Inject

class getCategoriesUseCase @Inject constructor(private val database: RemoteDataBase) {

    operator fun invoke() = database.getCategoryes()
}