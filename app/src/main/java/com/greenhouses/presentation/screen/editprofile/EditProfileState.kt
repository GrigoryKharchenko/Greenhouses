package com.greenhouses.presentation.screen.editprofile

import android.graphics.Bitmap
import androidx.annotation.StringRes

data class EditProfileState(
    @StringRes val nameMessage: Int? = null,
    @StringRes val cityMessage: Int? = null,
    @StringRes val birthdayMessage: Int? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val avatar: Bitmap? = null,
    val city: String = "",
    val name: String = "",
    val birthday: String = ""
)

