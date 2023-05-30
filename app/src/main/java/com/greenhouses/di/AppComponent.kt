package com.greenhouses.di

import android.content.Context
import com.greenhouses.GreenhousesApp
import com.greenhouses.di.module.ActivityModule
import com.greenhouses.di.module.FragmentModule
import com.greenhouses.di.module.InteractorModule
import com.greenhouses.di.module.PreferenceModule
import com.greenhouses.di.module.RepositoryModule
import com.greenhouses.di.module.RetrofitModule
import com.greenhouses.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        ActivityModule::class,
        FragmentModule::class,
        PreferenceModule::class,
        RetrofitModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        InteractorModule::class,
    ]
)
interface AppComponent : AndroidInjector<GreenhousesApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
