package com.greenhouses.presentation.screen.profile

sealed interface ProfileEvent {

    object Logout : ProfileEvent
}
