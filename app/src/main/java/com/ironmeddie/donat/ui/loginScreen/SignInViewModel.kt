package com.ironmeddie.donat.ui.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel (private val insertUser: InsertUserToDB, private val currentUser: NewCurrentUser) : ViewModel() {

    private val _firstName = MutableStateFlow("")
    val firstName = _firstName.asStateFlow()
    private val _lastName = MutableStateFlow("")
    val lastName = _lastName.asStateFlow()
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _eventFLow = MutableSharedFlow<Logged>()
    val eventFLow = _eventFLow.asSharedFlow()

    fun insert() {
        viewModelScope.launch {
            if (_email.value.isEmail()){
                val user = User(
                    firstName = _firstName.value,
                    lastName = _lastName.value,
                    email = _email.value,
                    password = "",
                    avatar = ""
                )
                try {
                    insertUser(user)
                    _eventFLow.emit(Logged.Success)
                    currentUser(user)
                }catch (e: Throwable){
                    e.message?.let {
                        if (it.contains("UNIQUE constraint failed"))
                            _eventFLow.emit(Logged.Failure("User with firstname ${firstName.value} already exist. Do you want to log in?"))
                    }

                }
            }else{
                _eventFLow.emit(Logged.Failure("incorrect email"))
            }


        }
    }

    fun updateField(update: UpdateField){
        viewModelScope.launch {
            if (!update.str.contains("\n")){
                when(update){
                    is UpdateField.First-> _firstName.value = update.firstName
                    is UpdateField.Last-> _lastName.value = update.lastName
                    is UpdateField.Email-> {
                        if (!update.email.isEmail()) _eventFLow.emit(Logged.Failure("incorrect email")) else _eventFLow.emit(Logged.Failure(""))
                        _email.value = update.email
                    }
                }
            }
        }
    }

}

sealed class UpdateField(val str:String){
    data class First(val firstName:String): UpdateField(firstName)
    data class Last(val lastName:String): UpdateField(lastName)
    data class Email(val email:String): UpdateField(email)
}