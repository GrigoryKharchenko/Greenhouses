package com.greenhouses.presentation.screen.authorization

sealed interface AuthorizationEvent {

    data class SendPhoneNumber(val phoneNumber: String): AuthorizationEvent
}
