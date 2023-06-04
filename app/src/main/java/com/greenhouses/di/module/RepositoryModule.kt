package com.greenhouses.di.module

import com.greenhouses.data.interceptor.InterceptorRepositoryImpl
import com.greenhouses.data.repository.AuthorizationRepositoryImpl
import com.greenhouses.data.repository.UserRepositoryImpl
import com.greenhouses.domain.repository.InterceptorRepository
import com.greenhouses.domain.repository.AuthorizationRepository
import com.greenhouses.domain.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindAuthorizationRepository(impl: AuthorizationRepositoryImpl): AuthorizationRepository

    @Binds
    fun bindProfileRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindInterceptorRepository(impl: InterceptorRepositoryImpl): InterceptorRepository
}
