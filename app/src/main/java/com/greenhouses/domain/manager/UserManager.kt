package com.greenhouses.domain.manager

import kotlinx.coroutines.flow.Flow

interface UserManager {

    suspend fun authorized(): Flow<String>

    suspend fun logOut()
}
