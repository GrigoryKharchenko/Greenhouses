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

        if (!request.refreshToken.isNullOrEmpty() && !request.accessToken.isNullOrEmpty()) {
            preferenceManagerRepository.setRefreshToken(request.refreshToken)
            preferenceManagerRepository.setAccessToken(request.accessToken)
        }

        return CodeConfirmModel(
            isUserExist = request.isUserExists,
        )
    }

    override suspend fun setAuthorized(isAuthorized: Boolean) {
        preferenceManagerRepository.setAuthorized(isAuthorized)
    }
}
