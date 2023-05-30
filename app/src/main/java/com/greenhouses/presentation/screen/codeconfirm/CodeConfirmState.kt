package com.greenhouses.presentation.screen.codeconfirm


sealed class CodeConfirmState {

    data class CodeConfirm(
        val hasCodeError: Boolean = false,
        val isLoading: Boolean = false,
    ) : CodeConfirmState()

    object OpenRegistrationScreen : CodeConfirmState()

    object OpenProfileScreen : CodeConfirmState()

    object Error : CodeConfirmState()
}
