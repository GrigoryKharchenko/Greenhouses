package com.greenhouses.data.repository

import com.greenhouses.data.GreenhousesApi
import com.greenhouses.data.request.CodeRequest
import com.greenhouses.data.request.PhoneRequest
import com.greenhouses.data.request.UserRequest
import com.greenhouses.data.response.CodeResponse
import com.greenhouses.data.response.PhoneResponse
import com.greenhouses.data.response.UserResponse
import com.greenhouses.domain.repository.AuthorizationRepository
import javax.inject.Inject

class AuthorizationRepositoryImpl @Inject constructor(
    private val greenhousesApi: GreenhousesApi
) : AuthorizationRepository {

    override suspend fun sendPhoneNumber(phone: String): PhoneResponse =
        greenhousesApi.sendPhoneNumber(PhoneRequest(phone))

    override suspend fun checkAuthCode(phone: String, code: String): CodeResponse =
        greenhousesApi.checkAuthCode(CodeRequest(phone = phone, code = code))

    override suspend fun sendUserInfo(phone: String, name: String, login: String): UserResponse =
        greenhousesApi.sendUserRegister(UserRequest(phone = phone, name = name, username = login))
}
