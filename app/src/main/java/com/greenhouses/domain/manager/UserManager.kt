package com.greenhouses.domain.manager

import kotlinx.coroutines.flow.Flow

interface UserManager {

    suspend fun isAuthorized(): Boolean

    suspend fun logOut()
}
