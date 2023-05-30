package com.greenhouses.domain.repository

interface PreferenceManagerRepository {

    suspend fun getAccessToken(): String
    suspend fun setAccessToken(accessToken: String)

    suspend fun getRefreshToken(): String
    suspend fun setRefreshToken(refreshToken: String)
}
