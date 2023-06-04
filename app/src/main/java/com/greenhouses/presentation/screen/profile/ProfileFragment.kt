package com.greenhouses.presentation.screen.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.greenhouses.R
import com.greenhouses.databinding.FragmentProfileBinding
import com.greenhouses.di.ViewModelFactory
import com.greenhouses.extension.addFragment
import com.greenhouses.extension.addNewChainFragment
import com.greenhouses.extension.launchWhenStarted
import com.greenhouses.extension.replaceFragment
import com.greenhouses.presentation.base.BaseFragment
import com.greenhouses.presentation.screen.authorization.AuthorizationFragment
import com.greenhouses.presentation.screen.editprofile.EditProfileFragment
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var defaultViewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelFactory)[ProfileViewModel::class.java]
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
        launchWhenStarted(viewModel.command, ::handleCommand)
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
            viewModel.perform(ProfileEvent.Logout)
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
                is ProfileState.Success -> successState(state)
                ProfileState.Error -> flProgress.isVisible = false
            }
        }
    }

    private fun successState(successState: ProfileState.Success) {
        with(binding) {
            flProgress.isVisible = false
            tvCity.text = successState.profileModel.city
            tvBirthday.text = successState.profileModel.birthday
            tvLogin.text = successState.profileModel.username
            tvName.text = successState.profileModel.name
            tvPhone.text = successState.profileModel.phone
            successState.profileModel.avatar?.let(ivAvatar::setImageBitmap)
        }
    }

    private fun handleCommand(command: ProfileCommand) {
        when (command) {
            ProfileCommand.OpenAuthorization -> {
                addNewChainFragment<AuthorizationFragment>(R.id.container)
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
