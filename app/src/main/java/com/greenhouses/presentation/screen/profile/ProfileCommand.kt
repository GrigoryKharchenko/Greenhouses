package com.greenhouses.presentation.screen.profile

sealed interface ProfileCommand {
    object OpenAuthorization: ProfileCommand
}
