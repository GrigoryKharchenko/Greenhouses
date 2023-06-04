package com.greenhouses.presentation.screen.codeconfirm

sealed interface CodeConfirmCommand {

    object OpenRegistrationScreen : CodeConfirmCommand

    object OpenProfileScreen : CodeConfirmCommand
}
