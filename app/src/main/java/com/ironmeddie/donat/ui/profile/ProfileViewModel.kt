package com.ironmeddie.donat.ui.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.ironmeddie.donat.data.auth.AuthResult
import com.ironmeddie.donat.domain.ResetBalance
import com.ironmeddie.donat.domain.SyncResult
import com.ironmeddie.donat.domain.auth.ChangeName
import com.ironmeddie.donat.domain.auth.CurrentUser
import com.ironmeddie.donat.domain.auth.SignOut
import com.ironmeddie.donat.domain.auth.changeProfileAvatar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val signOut: SignOut,
    private val currentUser: CurrentUser,
    private val changeAvatar: changeProfileAvatar,
    private val changeName: ChangeName,
    private val resetBalance: ResetBalance

    ) : ViewModel() {

    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user = _user.asStateFlow()

    private val _firstName = MutableStateFlow<String>("")
    val firstName = _firstName.asStateFlow()

    private val _resetResult = MutableSharedFlow<SyncResult>()
    val resetResult = _resetResult.asSharedFlow()

    private val _logOut = MutableStateFlow<AuthResult>(AuthResult.Loading)
    val logOut = _logOut.asStateFlow()


    private val _uploadResult = MutableSharedFlow<AuthResult>()
    val uploadResult = _uploadResult.asSharedFlow()
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
        viewModelScope.launch {
            currentUser().collect {
                _user.value = it
                _firstName.value = it?.displayName ?: ""
            }
        }
    }

    fun updateUser(uri: Uri?) {
        CoroutineScope(Dispatchers.IO).launch {
            if (uri != null) changeAvatar(uri).collectLatest {
                _uploadResult.emit(it)
            }
        }
    }

    fun firstnameChange(name: String) {
        if (!name.contains("\n")){
            _firstName.value = name
        }
    }

    fun uploadNewFirstName() {
        CoroutineScope(Dispatchers.IO).launch {
            changeName(firstName.value).collectLatest {
                _uploadResult.emit(it)
            }
        }
    }

    fun reset(){
        CoroutineScope(Dispatchers.IO).launch {
            resetBalance().collectLatest {
                _resetResult.emit(it)
            }
        }
    }


}