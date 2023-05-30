package com.greenhouses.presentation.screen.codeconfirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.greenhouses.domain.interactor.AuthorizationInteractor
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CodeConfirmViewModel @AssistedInject constructor(
    private val authorizationInteractor: AuthorizationInteractor,
    @Assisted private val phoneNumber: String
) : ViewModel() {

    private val _state = MutableStateFlow<CodeConfirmState>(CodeConfirmState.CodeConfirm())
    val state = _state.asStateFlow()

    fun checkCode(code: String) {
        when {
            code.length == 1 -> {
                _state.update {
                    CodeConfirmState.CodeConfirm().copy(hasCodeError = false)
                }
            }
            code.length < SIZE_CODE -> return
            code == CODE_CORRECT -> checkUser(code)
            else -> {
                _state.update {
                    CodeConfirmState.CodeConfirm().copy(hasCodeError = true)
                }
            }
        }
    }

    private fun checkUser(code: String) {
        viewModelScope.launch {
            runCatching {
                authorizationInteractor.checkAuthCode(phone = phoneNumber, code = code)
            }.onSuccess { codeConfirmModel ->
                if (codeConfirmModel.isUserExist) {
                    _state.emit(CodeConfirmState.OpenProfileScreen)
                } else {
                    _state.emit(CodeConfirmState.OpenRegistrationScreen)
                }
            }.onFailure {
                _state.emit(CodeConfirmState.Error)
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
