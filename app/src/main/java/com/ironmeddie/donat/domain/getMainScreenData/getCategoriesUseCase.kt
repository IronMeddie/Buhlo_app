package com.ironmeddie.donat.domain.getMainScreenData

import com.ironmeddie.donat.data.RemoteData

class getCategoriesUseCase(private val database: RemoteData) {

    operator fun invoke() = database.getCategoryes()
}