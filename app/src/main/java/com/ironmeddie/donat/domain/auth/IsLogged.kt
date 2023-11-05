package com.ironmeddie.donat.domain.auth

import com.ironmeddie.donat.data.auth.AuthResult
import com.ironmeddie.donat.data.auth.Authorization
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IsLogged @Inject constructor(private val authorization: Authorization) {

    operator fun invoke() = authorization.getCurrent().flatMapLatest {
        flow {
            if (it == null) emit(AuthResult.Failure("not logged"))
            else emit(AuthResult.Success)
        }
    }
}