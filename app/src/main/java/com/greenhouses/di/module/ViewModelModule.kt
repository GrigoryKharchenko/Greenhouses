package com.greenhouses.di.module

import androidx.lifecycle.ViewModel
import com.greenhouses.di.ViewModelKey
import com.greenhouses.presentation.activity.MainViewModel
import com.greenhouses.presentation.screen.authorization.AuthorizationViewModel
import com.greenhouses.presentation.screen.editprofile.EditProfileViewModel
import com.greenhouses.presentation.screen.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthorizationViewModel::class)
    fun bindAuthorizationViewModel(viewModel: AuthorizationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileViewModel::class)
    fun bindEditProfileViewModel(viewModel: EditProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}
