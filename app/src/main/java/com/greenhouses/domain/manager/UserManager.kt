package com.greenhouses.domain.manager

interface UserManager {

    suspend fun isAuthorized(): Boolean

    suspend fun logOut()
}
