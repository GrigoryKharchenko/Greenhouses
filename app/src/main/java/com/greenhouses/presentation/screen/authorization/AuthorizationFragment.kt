package com.greenhouses.presentation.screen.authorization

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.greenhouses.R
import com.greenhouses.databinding.FragmentAuthorizationBinding
import com.greenhouses.di.ViewModelFactory
import com.greenhouses.extension.addFragmentWithArgs
import com.greenhouses.extension.launchWhenStarted
import com.greenhouses.presentation.screen.codeconfirm.CodeConfirmFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class AuthorizationFragment : Fragment(), HasAndroidInjector {

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var defaultViewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelFactory)[AuthorizationViewModel::class.java]
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
        _binding = FragmentAuthorizationBinding.inflate(layoutInflater)
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
            btnSendCode.setOnClickListener {
                viewModel.sendPhoneNumber(etContactNumber.text.toString())
            }
            btnRefresh.setOnClickListener {
                groupError.isVisible = false
                groupSuccess.isVisible = true
                etContactNumber.setText("")
            }
        }
    }

    private fun handleCommand(command: AuthorizationCommand) {
        when (command) {
            AuthorizationCommand.OpenSmsCodeScreen -> {
                openCodeConfirmFragment()
            }
            AuthorizationCommand.Error -> {
                binding.groupError.isVisible = true
                binding.groupSuccess.isVisible = false
            }
        }
    }

    private fun openCodeConfirmFragment() {
        addFragmentWithArgs<CodeConfirmFragment>(
            containerId = R.id.container,
            args = bundleOf(CodeConfirmFragment.PHONE_NUMBER to binding.etContactNumber.text.toString())
        )
    }
}
