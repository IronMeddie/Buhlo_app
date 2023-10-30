package com.ironmeddie.donat.utils

import com.ironmeddie.donat.data.RemoteData
import com.ironmeddie.donat.domain.getMainScreenData.getCategoriesUseCase

object di {

    fun returnDatabase() = RemoteData()

    fun getMaiScreenUseCases() = getCategoriesUseCase(returnDatabase())


}