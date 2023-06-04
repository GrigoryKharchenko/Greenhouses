package com.greenhouses.di.module

import com.greenhouses.data.interactor.AuthorizationInteractorImpl
import com.greenhouses.data.interactor.UserInteractorImpl
import com.greenhouses.data.interceptor.TokenAuthenticatorImpl
import com.greenhouses.domain.interactor.AuthorizationInteractor
import com.greenhouses.domain.interactor.UserInteractor
import dagger.Binds
import dagger.Module
import okhttp3.Authenticator

@Module
interface InteractorModule {

    @Binds
    fun bindCodeConfirmInteractor(impl: AuthorizationInteractorImpl): AuthorizationInteractor

    @Binds
    fun bindServiceToken(impl: TokenAuthenticatorImpl): Authenticator

    @Binds
    fun bindUserInteractor(impl: UserInteractorImpl): UserInteractor
}
