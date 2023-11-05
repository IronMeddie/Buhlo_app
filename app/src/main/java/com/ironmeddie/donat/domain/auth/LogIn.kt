package com.ironmeddie.donat.domain.auth

import com.ironmeddie.donat.data.auth.Authorization
import javax.inject.Inject

class LogIn @Inject constructor(private val auth: Authorization) {

    operator fun invoke(login:String, password: String) = auth.logIn(login, password)
}