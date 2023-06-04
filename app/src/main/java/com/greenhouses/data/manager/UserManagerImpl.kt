package com.greenhouses.data.manager

import com.greenhouses.domain.manager.UserManager
import com.greenhouses.domain.repository.PreferenceManagerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserManagerImpl @Inject constructor(
    private val preferenceManagerRepository: PreferenceManagerRepository
) : UserManager {

    override suspend fun isAuthorized(): Boolean =
        preferenceManagerRepository.isAuthorized()

    override suspend fun logOut() {
        preferenceManagerRepository.clear()
    }
}
