package com.greenhouses.presentation.model

import android.os.Parcelable
import com.greenhouses.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfoModel(
    val name: String = "",
    val login: String = "",
    val birthday: String = "",
    val city: String = "",
    val avatar: String = "",
    val phone: String = "",
    val zodiac: Int = R.string.empty,
) : Parcelable
