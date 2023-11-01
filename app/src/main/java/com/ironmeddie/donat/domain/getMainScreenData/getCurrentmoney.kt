package com.ironmeddie.donat.domain.getMainScreenData

import com.ironmeddie.donat.data.RemoteData

class getCurrentmoney(val database: RemoteData) {

    operator fun invoke() = database.getCurrentMoney()
}