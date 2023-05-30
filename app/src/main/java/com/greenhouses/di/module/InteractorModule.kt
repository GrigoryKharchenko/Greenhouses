package com.greenhouses.di.module

import com.greenhouses.data.interactor.AuthorizationInteractorImpl
import com.greenhouses.domain.interactor.AuthorizationInteractor
import dagger.Binds
import dagger.Module

@Module
interface InteractorModule {

    @Binds
    fun bindCodeConfirmInteractor(impl: AuthorizationInteractorImpl): AuthorizationInteractor
}
