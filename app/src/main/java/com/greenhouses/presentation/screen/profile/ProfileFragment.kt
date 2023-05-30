package com.greenhouses.presentation.screen.profile

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.greenhouses.R
import com.greenhouses.databinding.FragmentProfileBinding
import com.greenhouses.di.ViewModelFactory
import com.greenhouses.extension.addFragment
import com.greenhouses.extension.launchWhenStarted
import com.greenhouses.extension.replaceFragment
import com.greenhouses.presentation.screen.authorization.AuthorizationFragment
import com.greenhouses.presentation.screen.editprofile.EditProfileFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ProfileFragment : Fragment(), HasAndroidInjector {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var defaultViewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelFactory)[ProfileViewModel::class.java]
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchWhenStarted(viewModel.userInfo, ::handleState)
        binding.toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.edit -> {
                    openEditProfileScreen()
                    true
                }
                else -> false
            }
        }
        binding.btnLogOut.setOnClickListener {
            viewModel.logUot()
            openAuthorizationScreen()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleState(state: ProfileState) {
        with(binding) {
            when (state) {
                ProfileState.Loading -> flProgress.isVisible = true
                is ProfileState.Success -> {
                    flProgress.isVisible = false
                    tvCity.text = state.profileModel.profileModel.city
                    tvBirthday.text = state.profileModel.profileModel.birthday
                    tvLogin.text = state.profileModel.profileModel.username
                    tvName.text = state.profileModel.profileModel.name
                    tvPhone.text = state.profileModel.profileModel.phone
                }
                ProfileState.Error -> flProgress.isVisible = false
            }
        }
    }

    private fun openEditProfileScreen() {
        addFragment<EditProfileFragment>(R.id.container)
    }

    private fun openAuthorizationScreen() {
        replaceFragment<AuthorizationFragment>(R.id.container)
    }
}
