package com.greenhouses.presentation.screen.codeconfirm

sealed interface CodeConfirmEvent {
    data class CheckCode(val code: String): CodeConfirmEvent
}
