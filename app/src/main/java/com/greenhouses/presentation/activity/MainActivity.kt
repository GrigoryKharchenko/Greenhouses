package com.greenhouses.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.greenhouses.R
import com.greenhouses.di.ViewModelFactory
import com.greenhouses.presentation.screen.authorization.AuthorizationFragment
import com.greenhouses.presentation.screen.profile.ProfileFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var defaultViewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelFactory)[MainViewModel::class.java]
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.command.collect(::handleCommand)
                }
            }
        }
    }

    private fun handleCommand(command: CommandActivity) {
        when (command) {
            CommandActivity.OpenAuthorizationScreen -> openAuthorizationScreen()
            CommandActivity.OpenProfileScreen -> openProfileScreen()
        }
    }

    private fun openAuthorizationScreen() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<AuthorizationFragment>(R.id.container)
        }
    }

    private fun openProfileScreen() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ProfileFragment>(R.id.container)
        }
    }
}
