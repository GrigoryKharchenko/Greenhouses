package com.greenhouses.domain.repository

import com.greenhouses.data.request.UserUpdatedRequest
import com.greenhouses.data.response.CodeResponse
import com.greenhouses.data.response.PhoneResponse
import com.greenhouses.data.response.TokenResponse

interface AuthorizationRepository {

    suspend fun sendPhoneNumber(phone: String): PhoneResponse

    suspend fun checkAuthCode(phone: String, code: String): CodeResponse

    suspend fun sendRefreshToken(refreshToken: String): TokenResponse
}
