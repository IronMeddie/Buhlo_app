package com.ironmeddie.donat.domain.auth

import android.net.Uri
import com.ironmeddie.donat.data.auth.Authorization
import javax.inject.Inject

class changeProfileAvatar @Inject constructor(private val auth: Authorization) {

    operator fun invoke(uri: Uri) = auth.changeAvater(uri)
}