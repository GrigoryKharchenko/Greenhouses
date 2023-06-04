package com.greenhouses.data.interceptor

import com.greenhouses.domain.manager.UserManager
import com.greenhouses.domain.repository.AuthorizationRepository
import com.greenhouses.domain.repository.PreferenceManagerRepository
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticatorImpl @Inject constructor(
    private val preferenceManagerRepository: PreferenceManagerRepository,
    private val userManager: UserManager,
    private val authorizationRepository: Lazy<AuthorizationRepository>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val autoRepo = authorizationRepository.get() ?: return null
        return runBlocking {
            try {
                val token = autoRepo.sendRefreshToken(preferenceManagerRepository.getRefreshToken())
                preferenceManagerRepository.setAccessToken(token.accessToken)
                preferenceManagerRepository.setRefreshToken(token.refreshToken)
                response.request.newBuilder().header("Authorization", "Bearer ${token.refreshToken}").build()
            } catch (e: Exception) {
                userManager.logOut()
                null
            }
        }
    }
}
