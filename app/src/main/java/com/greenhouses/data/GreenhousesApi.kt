package com.greenhouses.data

import com.greenhouses.data.request.CodeRequest
import com.greenhouses.data.request.PhoneRequest
import com.greenhouses.data.response.CodeResponse
import com.greenhouses.data.response.PhoneResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GreenhousesApi {

    @POST("api/v1/users/send-auth-code/")
    suspend fun sendPhoneNumber(
        @Body phoneRequest: PhoneRequest
    ): PhoneResponse

    @POST("api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(
        @Body codeRequest: CodeRequest
    ): CodeResponse
}
