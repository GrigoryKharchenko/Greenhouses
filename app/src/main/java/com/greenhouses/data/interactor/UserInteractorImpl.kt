package com.greenhouses.data.interactor

import com.greenhouses.data.request.UserUpdatedRequest
import com.greenhouses.domain.interactor.UserInteractor
import com.greenhouses.domain.repository.PreferenceManagerRepository
import com.greenhouses.domain.repository.UserRepository
import com.greenhouses.presentation.model.UserInfoModel
import javax.inject.Inject

class UserInteractorImpl @Inject constructor(
    private val preferenceManagerRepository: PreferenceManagerRepository,
    private val userRepository: UserRepository,
) : UserInteractor {

    override suspend fun sendUserInfo(phone: String, name: String, login: String) {
        val request = userRepository.sendUserInfo(phone, name, login)
        preferenceManagerRepository.setDataUser(
            phone = phone,
            name = name,
            login = login,
            refreshToken = request.refreshToken,
            accessToken = request.accessToken
        )
    }

    override suspend fun updateUserInfo(userUpdatedRequest: UserUpdatedRequest) {
        userRepository.updateUserInfo(userUpdatedRequest)
    }

    override suspend fun updateDataUserInfo(name: String, city: String, birthday: String, photoBase64: String) {
        preferenceManagerRepository.updateDataUserInfo(
            name = name,
            city = city,
            birthday = birthday,
            photoBase64 = photoBase64,
        )
    }

    override suspend fun getUserInfo(): UserInfoModel =
        preferenceManagerRepository.getUserInfo()
}
