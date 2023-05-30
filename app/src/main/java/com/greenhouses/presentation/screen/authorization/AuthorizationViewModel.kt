package com.greenhouses.presentation.screen.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenhouses.domain.repository.AuthorizationRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val authorization: AuthorizationRepository
) : ViewModel() {

    private val _command = MutableSharedFlow<AuthorizationCommand>()
    val command = _command.asSharedFlow()

    fun sendPhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            runCatching {
                authorization.sendPhoneNumber(phoneNumber)
            }.onSuccess { phoneResponse ->
                if (phoneResponse.isSuccess) {
                    _command.emit(AuthorizationCommand.OpenSmsCodeScreen)
                }
            }.onFailure {
                _command.emit(AuthorizationCommand.Error)
            }
        }
    }
}
