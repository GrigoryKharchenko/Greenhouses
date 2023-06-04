package com.greenhouses.domain.repository

import com.greenhouses.data.request.UserUpdatedRequest
import com.greenhouses.data.response.ProfileDataResponse
import com.greenhouses.data.response.TokenResponse

interface UserRepository {

    suspend fun getUserInfo(): ProfileDataResponse

    suspend fun sendUserInfo(phone: String, name: String, login: String): TokenResponse

    suspend fun updateUserInfo(updatedRequest: UserUpdatedRequest)
}
