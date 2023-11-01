package com.ironmeddie.donat.domain

import com.ironmeddie.donat.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetCurrentUser () {
    operator fun invoke(): Flow<User?> {
        // todo
        return flowOf()
    }
}