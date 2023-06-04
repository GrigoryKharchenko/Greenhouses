package com.greenhouses.data.repository

import com.greenhouses.data.GreenhousesApi
import com.greenhouses.data.request.UserRequest
import com.greenhouses.data.request.UserUpdatedRequest
import com.greenhouses.data.response.ProfileDataResponse
import com.greenhouses.data.response.TokenResponse
import com.greenhouses.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val greenhousesApi: GreenhousesApi
) : UserRepository {

    override suspend fun getUserInfo(): ProfileDataResponse =
        greenhousesApi.getProfile()

    override suspend fun sendUserInfo(phone: String, name: String, login: String): TokenResponse =
        greenhousesApi.sendUserRegister(UserRequest(phone = phone, name = name, username = login))

    override suspend fun updateUserInfo(updatedRequest: UserUpdatedRequest) {
        greenhousesApi.updateProfile(updatedRequest)
    }
}
