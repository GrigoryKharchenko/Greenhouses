package com.greenhouses.presentation.screen.editprofile

import androidx.annotation.StringRes

sealed interface EditProfileCommand {

    object OpenProfileScreen : EditProfileCommand

    object Error : EditProfileCommand

    data class ErrorValidationUiModel(
        @StringRes val nameMessage: Int?,
        @StringRes val cityMessage: Int?,
        @StringRes val birthdayMessage: Int?,
    ) : EditProfileCommand

}
