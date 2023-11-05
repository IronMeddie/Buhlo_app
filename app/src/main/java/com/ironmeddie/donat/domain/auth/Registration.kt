package com.ironmeddie.donat.domain.auth

import com.ironmeddie.donat.data.auth.Authorization
import com.ironmeddie.donat.models.User
import javax.inject.Inject

class Registration @Inject constructor(private val auth: Authorization) {
    
    operator fun invoke(login: String, password: String, user: User)= auth.registration(login, password, user)
}