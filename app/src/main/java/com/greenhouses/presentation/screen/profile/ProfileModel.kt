package com.greenhouses.presentation.screen.profile

import android.graphics.Bitmap
import androidx.annotation.StringRes

data class ProfileModel(
    val name: String,
    val username: String,
    val birthday: String,
    val city: String,
    val avatar: Bitmap?,
    val phone: String,
    @StringRes val zodiac: Int
)
