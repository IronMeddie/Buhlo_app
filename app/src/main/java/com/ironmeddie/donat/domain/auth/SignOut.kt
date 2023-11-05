package com.ironmeddie.donat.domain.auth

import com.ironmeddie.donat.data.auth.Authorization
import javax.inject.Inject

class SignOut @Inject constructor(private val auth: Authorization) {

    operator fun invoke() = auth.logOut()
}