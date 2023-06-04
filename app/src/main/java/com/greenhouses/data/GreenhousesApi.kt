package com.greenhouses.data

import com.greenhouses.data.request.CodeRequest
import com.greenhouses.data.request.PhoneRequest
import com.greenhouses.data.request.RefreshTokenRequest
import com.greenhouses.data.request.UserRequest
import com.greenhouses.data.request.UserUpdatedRequest
import com.greenhouses.data.response.CodeResponse
import com.greenhouses.data.response.PhoneResponse
import com.greenhouses.data.response.ProfileDataResponse
import com.greenhouses.data.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface GreenhousesApi {

    @POST("api/v1/users/send-auth-code/")
    suspend fun sendPhoneNumber(
        @Body phoneRequest: PhoneRequest
    ): PhoneResponse

    @POST("api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(
        @Body codeRequest: CodeRequest
    ): CodeResponse

    @POST("api/v1/users/register/")
    suspend fun sendUserRegister(
        @Body userRequest: UserRequest
    ): TokenResponse

    @GET("api/v1/users/me/")
    suspend fun getProfile(): ProfileDataResponse

    @PUT("api/v1/users/me/")
    suspend fun updateProfile(
        @Body userUpdatedRequest: UserUpdatedRequest
    )

    @POST("api/v1/users/refresh-token/")
    suspend fun sendRefreshToken(
        @Body phoneRequest: RefreshTokenRequest
    ): TokenResponse
}
