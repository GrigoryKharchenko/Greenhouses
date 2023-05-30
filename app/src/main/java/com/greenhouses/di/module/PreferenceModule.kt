package com.greenhouses.di.module

import com.greenhouses.data.repository.PreferenceManagerRepositoryImpl
import com.greenhouses.domain.repository.PreferenceManagerRepository
import dagger.Binds
import dagger.Module

@Module
interface PreferenceModule {

    @Binds
    fun bindPreferenceManager(preferenceManagerImpl: PreferenceManagerRepositoryImpl): PreferenceManagerRepository
}
