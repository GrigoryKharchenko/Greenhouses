package com.greenhouses.data.mapper

import com.greenhouses.data.response.ProfileResponse
import com.greenhouses.data.response.ProfileDataResponse
import com.greenhouses.presentation.screen.profile.ProfileDataModel
import com.greenhouses.presentation.screen.profile.ProfileModel

fun ProfileDataResponse.mapToModel(): ProfileDataModel =
    ProfileDataModel(profileModel = profileResponse.mapToProfileModel())

fun ProfileResponse.mapToProfileModel(): ProfileModel =
    ProfileModel(
        id = id,
        name = name,
        username = username,
        birthday = birthday ?: "",
        city = city ?: "",
        avatar = avatar ?: "",
        phone = phone
    )
