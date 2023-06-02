package com.greenhouses.domain.repository

import okhttp3.Interceptor
import okhttp3.Response

interface InterceptorRepository {

    fun addInterceptorToken(chain: Interceptor.Chain): Response
}
