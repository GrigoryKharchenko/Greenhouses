package com.greenhouses.di.module

import com.greenhouses.data.interceptor.InterceptorRepositoryImpl
import com.greenhouses.data.repository.AuthorizationRepositoryImpl
import com.greenhouses.data.repository.ProfileRepositoryImpl
import com.greenhouses.domain.repository.InterceptorRepository
import com.greenhouses.domain.repository.AuthorizationRepository
import com.greenhouses.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindAuthorizationRepository(impl: AuthorizationRepositoryImpl): AuthorizationRepository

    @Binds
    fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    fun bindInterceptorRepository(impl: InterceptorRepositoryImpl): InterceptorRepository
}
