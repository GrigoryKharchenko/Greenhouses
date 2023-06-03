package com.greenhouses.presentation.screen.codeconfirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.greenhouses.domain.interactor.AuthorizationInteractor
import com.greenhouses.domain.repository.PreferenceManagerRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CodeConfirmViewModel @AssistedInject constructor(
    private val authorizationInteractor: AuthorizationInteractor,
    @Assisted private val phoneNumber: String
) : ViewModel() {

    private val _state = MutableStateFlow(CodeConfirmState())
    val state = _state.asStateFlow()

    private val _command = MutableSharedFlow<CodeConfirmCommand>()
    val command = _command.asSharedFlow()

    fun perform(event: CodeConfirmEvent) {
        when (event) {
            is CodeConfirmEvent.CheckCode -> {
                checkCode(event.code)
            }
        }
    }

    private fun checkCode(code: String) {
        when {
            code.length == 1 -> {
                _state.update {
                    it.copy(hasCodeError = false)
                }
            }
            code.length < SIZE_CODE -> return
            code == CODE_CORRECT -> checkUser(code)
            else -> {
                _state.update {
                    it.copy(hasCodeError = true)
                }
            }
        }
    }

    private fun checkUser(code: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            runCatching {
                authorizationInteractor.checkAuthCode(phone = phoneNumber, code = code)
            }.onSuccess { codeConfirmModel ->
                if (codeConfirmModel.isUserExist) {
                    authorizationInteractor.setAuthorized(true)
                    _command.emit(CodeConfirmCommand.OpenProfileScreen)
                } else {
                    _command.emit(CodeConfirmCommand.OpenRegistrationScreen)
                }
            }.onFailure {
                _state.update {
                    it.copy(
                        isError = true,
                        isLoading = false
                    )
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(phoneNumber: String): CodeConfirmViewModel
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

        private const val CODE_CORRECT = "133337"
        private const val SIZE_CODE = 6
    }
}
