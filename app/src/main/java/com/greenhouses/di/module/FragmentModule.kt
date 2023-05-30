package com.greenhouses.di.module

import com.greenhouses.presentation.screen.authorization.AuthorizationFragment
import com.greenhouses.presentation.screen.codeconfirm.CodeConfirmFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun bindAuthorizationFragment(): AuthorizationFragment

    @ContributesAndroidInjector
    fun bindCodeConformFragment(): CodeConfirmFragment
}
