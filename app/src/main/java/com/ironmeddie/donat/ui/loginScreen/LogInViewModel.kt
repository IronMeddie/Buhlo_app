package com.ironmeddie.donat.ui.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.donat.data.auth.AuthResult
import com.ironmeddie.donat.domain.SyncDataUseCase
import com.ironmeddie.donat.domain.SyncResult
import com.ironmeddie.donat.domain.auth.LogIn
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
class LogInViewModel @Inject constructor(
    private val logIn: LogIn,
    private val sync: SyncDataUseCase
) : ViewModel() {

    private val _firstName = MutableStateFlow("")
    val firstName = _firstName.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _eventFLow = MutableSharedFlow<Logged>()
    val eventFLow = _eventFLow.asSharedFlow()

    private val _syncResult = MutableSharedFlow<SyncResult>()
    val syncResult = _syncResult.asSharedFlow()

    fun logIn() {
        viewModelScope.launch {
            logIn(firstName.value, password.value).collectLatest {

                when (it) {
                    is AuthResult.Success -> _eventFLow.emit(Logged.Success)
                    is AuthResult.Failure -> _eventFLow.emit(Logged.Failure(it.message))
                    else -> Unit
                }

            }
        }
    }

    fun updateFirstName(str: String) {
        if (!str.contains("\n")) _firstName.value = str
    }

    fun updatePassword(str: String) {
        if (!str.contains("\n")) _password.value = str
    }

    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            sync().collectLatest {
                _syncResult.emit(it)
            }
        }
    }
}

sealed class Logged {
    object Success : Logged()
    data class Failure(val message: String) : Logged()
}