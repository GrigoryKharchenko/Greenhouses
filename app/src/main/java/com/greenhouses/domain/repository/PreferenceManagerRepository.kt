package com.greenhouses.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceManagerRepository {

      suspend fun getAccessToken(): Flow<String>
    suspend fun setAccessToken(accessToken: String)

      suspend fun getRefreshToken(): String
    suspend fun setRefreshToken(refreshToken: String)

    suspend fun getPhone(): String

    //    suspend fun setPhone(phone: String)
//
    suspend fun getName(): String
//    suspend fun setName(name: String)

    suspend fun getLogin(): String
//    suspend fun setLogin(login: String)

    suspend fun setDataUser(phone: String, name: String, login: String, refreshToken: String, accessToken: String)
}
