package com.greenhouses.presentation.screen.editprofile

import androidx.annotation.StringRes

sealed interface EditProfileCommand {

    object OpenProfileScreen : EditProfileCommand
}
