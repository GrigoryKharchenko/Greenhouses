package com.greenhouses.presentation.screen.authorization

sealed interface AuthorizationCommand {

    object OpenSmsCodeScreen : AuthorizationCommand

    object Error : AuthorizationCommand
}
