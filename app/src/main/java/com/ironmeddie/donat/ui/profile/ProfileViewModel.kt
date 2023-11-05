package com.ironmeddie.donat.ui.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.donat.domain.auth.CurrentUser
import com.ironmeddie.donat.domain.auth.SignOut
import com.ironmeddie.donat.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logOut: SignOut,
//    private val updateAvatar: UpdateAvatar,
    private val currentUser: CurrentUser
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()


    init {
        getUser()
    }

    fun logOut() {
        CoroutineScope(Dispatchers.IO).launch {
            logOut.invoke()
        }
    }

    fun getUser() {
//        currentUser().onEach {
//            _user.value = it
//        }.launchIn(viewModelScope)
    }

    fun saveAvatar(uri: Uri) {
//        viewModelScope.launch {
//            val newUser = _user.value?.copy(avatar = uri.toString())
//            updateAvatar(newUser ?: return@launch)
//        }
    }


}