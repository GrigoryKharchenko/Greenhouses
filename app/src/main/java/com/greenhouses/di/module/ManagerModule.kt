package com.greenhouses.di.module

import com.greenhouses.data.manager.UserManagerImpl
import com.greenhouses.domain.manager.UserManager
import dagger.Binds
import dagger.Module

@Module
interface ManagerModule {

    @Binds
    fun bindUserManager(impl: UserManagerImpl): UserManager
}
