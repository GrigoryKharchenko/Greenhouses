package com.greenhouses.data.interceptor

import com.greenhouses.domain.repository.InterceptorRepository
import com.greenhouses.domain.repository.PreferenceManagerRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class InterceptorRepositoryImpl @Inject constructor(
    private val preferenceManagerRepository: PreferenceManagerRepository
) : InterceptorRepository {

    override fun addInterceptorToken(chain: Interceptor.Chain): Response {
        val token = runBlocking { preferenceManagerRepository.subscribeToGetAccessToken().first() }
        val request = chain.request().newBuilder().addHeader(
            "Authorization",
            "Bearer $token"
        ).build()
        return chain.proceed(request)
    }
}
