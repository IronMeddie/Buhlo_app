package com.ironmeddie.donat.domain.auth

import com.ironmeddie.donat.data.auth.Authorization
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class CurrentUser @Inject constructor(private val authorization: Authorization) {
    operator fun invoke() = authorization.getCurrent()
}