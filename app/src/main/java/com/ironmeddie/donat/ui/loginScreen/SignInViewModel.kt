package com.ironmeddie.donat.ui.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.donat.data.auth.AuthResult
import com.ironmeddie.donat.domain.SyncDataUseCase
import com.ironmeddie.donat.domain.SyncResult
import com.ironmeddie.donat.domain.auth.Registration
import com.ironmeddie.donat.models.User
import com.ironmeddie.donat.utils.isEmail
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
class SignInViewModel @Inject constructor(
    private val registration: Registration,
    private val sync: SyncDataUseCase
) : ViewModel() {

    private val _firstName = MutableStateFlow("")
    val firstName = _firstName.asStateFlow()

    private val _lastName = MutableStateFlow("")
    val lastName = _lastName.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _eventFLow = MutableSharedFlow<Logged>()
    val eventFLow = _eventFLow.asSharedFlow()

    private val _syncResult = MutableSharedFlow<SyncResult>()
    val syncResult = _syncResult.asSharedFlow()




    fun insert() {
        viewModelScope.launch {
            if (_email.value.isEmail()) {
                val user = User(
                    firstName = _firstName.value,
                    lastName = _lastName.value,
                    email = _email.value,
                )
                registration(_email.value, _lastName.value, user).collectLatest {

                    when (it) {
                        is AuthResult.Success -> _eventFLow.emit(Logged.Success)
                        is AuthResult.Failure -> _eventFLow.emit(Logged.Failure(it.message))
                        else -> Unit
                    }

                }


            } else {
                _eventFLow.emit(Logged.Failure("incorrect email"))
            }


        }
    }

    fun updateField(update: UpdateField) {
        viewModelScope.launch {
            if (!update.str.contains("\n")) {
                when (update) {
                    is UpdateField.First -> _firstName.value = update.firstName
                    is UpdateField.Last -> _lastName.value = update.lastName
                    is UpdateField.Email -> {
                        if (!update.email.isEmail()) _eventFLow.emit(Logged.Failure("incorrect email")) else _eventFLow.emit(
                            Logged.Failure("")
                        )
                        _email.value = update.email
                    }
                }
            }
        }
    }

    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            sync().collectLatest {
                _syncResult.emit(it)
            }
        }
    }

}

sealed class UpdateField(val str: String) {
    data class First(val firstName: String) : UpdateField(firstName)
    data class Last(val lastName: String) : UpdateField(lastName)
    data class Email(val email: String) : UpdateField(email)
}