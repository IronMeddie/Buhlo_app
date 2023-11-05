package com.ironmeddie.donat.ui.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.donat.data.auth.AuthResult
import com.ironmeddie.donat.domain.auth.CurrentUser
import com.ironmeddie.donat.domain.auth.SignOut
import com.ironmeddie.donat.domain.auth.changeProfileData
import com.ironmeddie.donat.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val signOut: SignOut,
//    private val updateAvatar: UpdateAvatar,
    private val currentUser: CurrentUser,
    private val changeProfileData: changeProfileData
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    val _logOut = MutableStateFlow<AuthResult>(AuthResult.Loading)
    val logOut = _logOut.asStateFlow()


    init {
        getUser()
    }

    fun logOut() {
        CoroutineScope(Dispatchers.IO).launch {
            signOut.invoke().collectLatest {
                _logOut.value = it
            }
        }
    }

    fun getUser() {
//        currentUser().onEach {
//            _user.value = it
//        }.launchIn(viewModelScope)
    }

    fun updateUser(uri: Uri?, name: String) {
        viewModelScope.launch {
        }
    }


}