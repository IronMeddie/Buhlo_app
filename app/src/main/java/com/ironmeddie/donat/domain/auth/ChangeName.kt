package com.ironmeddie.donat.domain.auth

import com.ironmeddie.donat.data.auth.Authorization
import javax.inject.Inject

class ChangeName @Inject constructor(private val auth: Authorization) {

    operator fun invoke(name: String) = auth.changeFirstName(name)
}