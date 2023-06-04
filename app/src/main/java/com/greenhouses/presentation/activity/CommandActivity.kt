package com.greenhouses.presentation.activity

sealed interface CommandActivity{

    object OpenProfileScreen: CommandActivity

    object OpenAuthorizationScreen: CommandActivity
}
