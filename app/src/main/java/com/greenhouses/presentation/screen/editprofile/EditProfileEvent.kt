package com.greenhouses.presentation.screen.editprofile

import android.graphics.Bitmap

sealed interface EditProfileEvent {

    data class SetAvatar(val bitmap: Bitmap) : EditProfileEvent

    data class UpdateUserInfo(val name: String, val city: String, val birthday: String) : EditProfileEvent
}
