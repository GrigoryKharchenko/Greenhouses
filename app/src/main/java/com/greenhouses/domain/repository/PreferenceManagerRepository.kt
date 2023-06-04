package com.greenhouses.domain.repository

import com.greenhouses.presentation.model.UserInfoModel
import kotlinx.coroutines.flow.Flow

interface PreferenceManagerRepository {

    suspend fun clear()

    suspend fun setAuthorized(isAuthorized: Boolean)

    suspend fun isAuthorized(): Boolean

    suspend fun subscribeToGetAccessToken(): Flow<String>

    suspend fun setAccessToken(accessToken: String)

    suspend fun getRefreshToken(): String

    suspend fun setRefreshToken(refreshToken: String)

    suspend fun getUserInfo(): UserInfoModel

    suspend fun subscribeToGetUserInfo(): Flow<UserInfoModel>

    suspend fun setDataUser(phone: String, name: String, login: String, refreshToken: String, accessToken: String)

    suspend fun setDataUser(userInfoModel: UserInfoModel)

    suspend fun updateDataUserInfo(
        name: String,
        birthday: String,
        city: String,
        photoBase64: String,
        zodiac: Int
    )
}
