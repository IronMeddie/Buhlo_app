package com.ironmeddie.donat.domain.auth

import com.ironmeddie.donat.data.auth.Authorization
import com.ironmeddie.donat.models.User
import javax.inject.Inject

class changeProfileData @Inject constructor(private val auth: Authorization) {

    operator fun invoke(user: User) = auth.changeUserInfo(user)
}