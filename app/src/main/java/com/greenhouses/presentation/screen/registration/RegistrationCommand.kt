package com.greenhouses.presentation.screen.registration

import androidx.annotation.StringRes

sealed interface RegistrationCommand {

    object Loading : RegistrationCommand

    object OpenProfileScreen : RegistrationCommand

    object Error : RegistrationCommand

    data class ErrorValidationUiModel(
        @StringRes val nameMessage: Int?,
        @StringRes val loginMessage: Int?
    ) : RegistrationCommand
}
