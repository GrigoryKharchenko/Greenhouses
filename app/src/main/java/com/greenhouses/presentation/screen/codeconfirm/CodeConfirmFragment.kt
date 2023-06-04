package com.greenhouses.presentation.screen.codeconfirm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.greenhouses.R
import com.greenhouses.databinding.FragmentCodeConfirmBinding
import com.greenhouses.extension.addNewChainFragment
import com.greenhouses.extension.launchWhenStarted
import com.greenhouses.extension.replaceFragmentWithArgs
import com.greenhouses.presentation.base.BaseFragment
import com.greenhouses.presentation.screen.profile.ProfileFragment
import com.greenhouses.presentation.screen.registration.RegistrationFragment
import javax.inject.Inject

class CodeConfirmFragment : BaseFragment() {

    private var _binding: FragmentCodeConfirmBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: CodeConfirmViewModel.Factory

    private val phoneNumber: String by lazy {
        requireNotNull(arguments?.getString(PHONE_NUMBER))
    }

    private val viewModel: CodeConfirmViewModel by viewModels {
        CodeConfirmViewModel.provideFactory(viewModelFactory, phoneNumber)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCodeConfirmBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            etCodeConform.doAfterTextChanged { text ->
                viewModel.perform(CodeConfirmEvent.CheckCode(text.toString()))
            }
            btnRefresh.setOnClickListener {
                binding.groupError.isVisible = false
                binding.tilCodeConform.isVisible = true
            }
        }
        launchWhenStarted(viewModel.state, ::handleState)
        launchWhenStarted(viewModel.command, ::handleCommand)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleState(state: CodeConfirmState) {
        with(binding) {
            groupError.isVisible = state.isError
            tilCodeConform.isVisible = !state.isError && !state.isLoading
            if (state.hasCodeError) {
                tilCodeConform.error = getString(R.string.error_code)
                etCodeConform.setText("")
            } else {
                tilCodeConform.error = null
            }
        }
    }

    private fun handleCommand(command: CodeConfirmCommand) {
        when (command) {
            CodeConfirmCommand.OpenRegistrationScreen -> {
                openRegistrationScreen()
            }
            CodeConfirmCommand.OpenProfileScreen -> {
                openProfileScreen()
            }
        }
    }

    private fun openRegistrationScreen() {
        replaceFragmentWithArgs<RegistrationFragment>(
            containerId = R.id.container,
            args = bundleOf(PHONE_NUMBER to phoneNumber)
        )
    }

    private fun openProfileScreen() {
        addNewChainFragment<ProfileFragment>(containerId = R.id.container)
    }

    companion object {
        const val PHONE_NUMBER = "phoneNumber"
    }
}
