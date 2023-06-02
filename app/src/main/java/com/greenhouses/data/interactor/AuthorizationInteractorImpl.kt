package com.greenhouses.data.interactor

import com.greenhouses.domain.interactor.AuthorizationInteractor
import com.greenhouses.domain.model.CodeConfirmModel
import com.greenhouses.domain.repository.AuthorizationRepository
import com.greenhouses.domain.repository.PreferenceManagerRepository
import javax.inject.Inject

class AuthorizationInteractorImpl @Inject constructor(
    private val preferenceManagerRepository: PreferenceManagerRepository,
    private val authorizationRepository: AuthorizationRepository,
) : AuthorizationInteractor {

    override suspend fun checkAuthCode(phone: String, code: String): CodeConfirmModel {
        val request = authorizationRepository.checkAuthCode(phone, code)
        return CodeConfirmModel(
            isUserExist = request.isUserExists,
        )
    }

    override suspend fun sendUserInfo(phone: String, name: String, login: String) {
        val request = authorizationRepository.sendUserInfo(phone, name, login)
        preferenceManagerRepository.setDataUser(
            phone = phone,
            name = name,
            login = login,
            refreshToken = request.refreshToken,
            accessToken = request.accessToken
        )
    }
}
