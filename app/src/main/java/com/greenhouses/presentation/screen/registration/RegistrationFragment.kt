package com.greenhouses.presentation.screen.registration

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.greenhouses.R
import com.greenhouses.databinding.FragmentRegistrationBinding
import com.greenhouses.extension.addFragment
import com.greenhouses.extension.launchWhenStarted
import com.greenhouses.presentation.screen.codeconfirm.CodeConfirmFragment
import com.greenhouses.presentation.screen.profile.ProfileFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class RegistrationFragment : Fragment(), HasAndroidInjector {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: RegistrationViewModel.Factory

    private val phoneNumber: String by lazy {
        requireNotNull(arguments?.getString(CodeConfirmFragment.PHONE_NUMBER))
    }

    private val viewModel: RegistrationViewModel by viewModels {
        RegistrationViewModel.provideFactory(viewModelFactory, phoneNumber)
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
        _binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        launchWhenStarted(viewModel.command, ::handleCommand)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUi() {
        with(binding) {
            tvTitleNumberPhone.text = getString(R.string.registration_title_number_phone, phoneNumber)
            btnRegister.setOnClickListener {
                viewModel.sendUserInfo(
                    name = etName.text.toString(),
                    login = etLogin.text.toString()
                )
            }
            btnRefresh.setOnClickListener {
                binding.groupSuccess.isVisible = true
                binding.groupError.isVisible = false
            }
            etName.doAfterTextChanged {
                tilName.error = null
            }
            etLogin.doAfterTextChanged {
                tilLogin.error = null
            }
            toolBar.setNavigationOnClickListener {
                goBack()
            }
        }
    }

    private fun handleCommand(command: RegistrationCommand) {
        when (command) {
            RegistrationCommand.OpenProfileScreen -> {
                openProfileScreen()
            }
            RegistrationCommand.Error -> {
                binding.flProgress.isVisible = false
                Toast.makeText(requireContext(), getString(R.string.error_toast), Toast.LENGTH_SHORT).show()
            }
            RegistrationCommand.Loading -> binding.flProgress.isVisible = true
            is RegistrationCommand.ErrorValidationUiModel -> {
                binding.tilName.error = command.nameMessage?.let(::getString)
                binding.tilLogin.error = command.loginMessage?.let(::getString)
            }
        }
    }

    private fun openProfileScreen() {
        addFragment<ProfileFragment>(containerId = R.id.container)
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }
}
