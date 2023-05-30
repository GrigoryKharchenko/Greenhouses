package com.greenhouses.di.module

import androidx.lifecycle.ViewModel
import com.greenhouses.di.ViewModelKey
import com.greenhouses.presentation.screen.authorization.AuthorizationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthorizationViewModel::class)
    fun bindAuthorizationViewModel(viewModel: AuthorizationViewModel): ViewModel
}
