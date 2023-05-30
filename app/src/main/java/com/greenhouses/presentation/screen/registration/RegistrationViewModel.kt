package com.greenhouses.presentation.screen.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.greenhouses.R
import com.greenhouses.domain.interactor.AuthorizationInteractor
import com.greenhouses.extension.isValidLogin
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RegistrationViewModel @AssistedInject constructor(
    private val authorizationInteractor: AuthorizationInteractor,
    @Assisted private val phoneNumber: String
) : ViewModel() {

    private val _command = MutableSharedFlow<RegistrationCommand>()
    val command = _command.asSharedFlow()

    fun sendUserInfo(name: String, login: String) {
        viewModelScope.launch {
            val validName = name.isNotEmpty()
            val validLogin = login.isValidLogin()
            if (validLogin && validName) {
                _command.emit(RegistrationCommand.Loading)
                runCatching {
                    authorizationInteractor.sendUserInfo(
                        phone = phoneNumber,
                        name = name,
                        login = login
                    )
                }.onSuccess {
                    _command.emit(RegistrationCommand.OpenProfileScreen)
                }.onFailure {
                    _command.emit(RegistrationCommand.Error)
                }
            } else {
                _command.emit(
                    RegistrationCommand.ErrorValidationUiModel(
                        nameMessage = if (validName) null else R.string.registration_name_error,
                        loginMessage = if (validLogin) null else R.string.registration_login_error
                    )
                )
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(phoneNumber: String): RegistrationViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            phoneNumber: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(phoneNumber) as T
            }
        }
    }
}
