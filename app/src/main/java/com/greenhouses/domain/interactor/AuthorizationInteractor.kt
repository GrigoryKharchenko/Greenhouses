package com.greenhouses.domain.interactor

import com.greenhouses.domain.model.CodeConfirmModel

interface AuthorizationInteractor {

    suspend fun checkAuthCode(phone: String, code: String): CodeConfirmModel

    suspend fun sendUserInfo(phone: String, name: String, login: String)
}
