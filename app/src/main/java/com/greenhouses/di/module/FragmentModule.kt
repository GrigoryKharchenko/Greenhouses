package com.greenhouses.di.module

import com.greenhouses.presentation.screen.authorization.AuthorizationFragment
import com.greenhouses.presentation.screen.codeconfirm.CodeConfirmFragment
import com.greenhouses.presentation.screen.editprofile.EditProfileFragment
import com.greenhouses.presentation.screen.profile.ProfileFragment
import com.greenhouses.presentation.screen.registration.RegistrationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun bindAuthorizationFragment(): AuthorizationFragment

    @ContributesAndroidInjector
    fun bindCodeConformFragment(): CodeConfirmFragment

    @ContributesAndroidInjector
    fun bindProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    fun bindRegistrationFragment(): RegistrationFragment

    @ContributesAndroidInjector
    fun bindEditProfileFragment(): EditProfileFragment
}
