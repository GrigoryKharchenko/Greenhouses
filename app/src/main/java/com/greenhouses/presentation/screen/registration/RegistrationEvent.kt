package com.greenhouses.presentation.screen.registration

sealed interface RegistrationEvent {

    data class SendUserInfo(val name: String, val login: String) : RegistrationEvent
}
