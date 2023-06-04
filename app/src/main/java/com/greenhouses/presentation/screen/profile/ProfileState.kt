package com.greenhouses.presentation.screen.profile

sealed interface ProfileState {

    object Loading : ProfileState

    object Error : ProfileState

    data class Success(
        val profileModel: ProfileModel
    ) : ProfileState
}
