package com.greenhouses.domain.interactor

import com.greenhouses.data.request.UserUpdatedRequest
import com.greenhouses.presentation.model.UserInfoModel

interface UserInteractor {

    suspend fun sendUserInfo(phone: String, name: String, login: String)

    suspend fun updateUserInfo(userUpdatedRequest: UserUpdatedRequest)

    suspend fun updateDataUserInfo(name: String, city: String, birthday: String, photoBase64: String,zodiac:Int)

    suspend fun getUserInfo(): UserInfoModel
}
