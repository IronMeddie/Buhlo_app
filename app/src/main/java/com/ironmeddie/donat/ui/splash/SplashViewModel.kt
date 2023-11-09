package com.ironmeddie.donat.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.donat.data.auth.AuthResult
import com.ironmeddie.donat.domain.SyncDataUseCase
import com.ironmeddie.donat.domain.SyncResult
import com.ironmeddie.donat.domain.auth.IsLogged
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
class SplashViewModel @Inject constructor(
    private val sync: SyncDataUseCase,
    private val isLogged: IsLogged
) : ViewModel() {

    private val _state = MutableSharedFlow<SyncResult>()
    val state = _state.asSharedFlow()

    private val _user = MutableStateFlow<AuthResult>(AuthResult.Loading)
    val user = _user.asStateFlow()

    init {
        getAuth()
    }

    fun syncData() {
        CoroutineScope(Dispatchers.IO).launch {
            sync.invoke().collectLatest {
                _state.emit(it)
            }
        }
    }

    fun getAuth() {
        viewModelScope.launch {
            isLogged().collectLatest {
                _user.value = it
            }
        }
    }
}