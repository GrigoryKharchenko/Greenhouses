package com.greenhouses.di.module

import com.greenhouses.data.repository.AuthorizationRepositoryImpl
import com.greenhouses.domain.repository.AuthorizationRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindNetworkRepository(impl: AuthorizationRepositoryImpl): AuthorizationRepository
}
