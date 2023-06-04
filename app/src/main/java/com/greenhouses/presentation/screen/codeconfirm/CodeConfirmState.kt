package com.greenhouses.presentation.screen.codeconfirm

data class CodeConfirmState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val hasCodeError: Boolean = false
)
