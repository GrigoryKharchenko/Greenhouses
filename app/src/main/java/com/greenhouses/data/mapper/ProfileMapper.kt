package com.greenhouses.data.mapper

import com.greenhouses.data.response.ProfileDataResponse
import com.greenhouses.presentation.model.UserInfoModel
import com.greenhouses.presentation.screen.profile.ProfileModel
import com.greenhouses.presentation.util.decodeToBitmap

fun UserInfoModel.mapToProfileModel(): ProfileModel =
    ProfileModel(
        name = name,
        username = login,
        birthday = birthday,
        city = city,
        avatar = avatar.decodeToBitmap(),
        phone = phone,
        zodiac = zodiac
    )

fun ProfileDataResponse.mapToUserInfoModel(): UserInfoModel =
    UserInfoModel(
        name = profileResponse.name,
        city = profileResponse.city ?: "",
        birthday = profileResponse.birthday ?: "",
        /**
        Отличются данные аватара, которые отправляются и получаются с бэка
        Поэтому аватар отображаться не будет после запроса getUserInfo
        */
        avatar = profileResponse.avatar ?: "",
        phone = profileResponse.phone,
        login = profileResponse.username,
    )
