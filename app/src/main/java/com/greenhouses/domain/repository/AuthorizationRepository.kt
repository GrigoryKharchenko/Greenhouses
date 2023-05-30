package com.greenhouses.domain.repository

import com.greenhouses.data.response.CodeResponse
import com.greenhouses.data.response.PhoneResponse

interface AuthorizationRepository {

    suspend fun sendPhoneNumber(phone: String): PhoneResponse

    suspend fun checkAuthCode(phone: String, code: String): CodeResponse
}
