package com.ironmeddie.donat.utils

import android.text.TextUtils

fun String.isEmail(): Boolean {
    return !TextUtils.isEmpty(this)
            && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

}