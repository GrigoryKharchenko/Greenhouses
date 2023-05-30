package com.greenhouses.presentation.screen.codeconfirm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.greenhouses.databinding.FragmentCodeConfirmBinding
import com.greenhouses.extension.launchWhenStarted
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CodeConfirmFragment : Fragment(), HasAndroidInjector {

    private var _binding: FragmentCodeConfirmBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: CodeConfirmViewModel.Factory

    private val phoneNumber: String by lazy {
        requireNotNull(arguments?.getString(PHONE_NUMBER))
    }

    private val viewModel: CodeConfirmViewModel by viewModels {
        CodeConfirmViewModel.provideFactory(viewModelFactory, phoneNumber)
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
        _binding = FragmentCodeConfirmBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            etCodeConform.doAfterTextChanged { text ->
                viewModel.checkCode(text.toString())
            }
            btnRefresh.setOnClickListener {
                binding.groupError.isVisible = false
                binding.groupSuccess.isVisible = true
            }
        }
        launchWhenStarted(viewModel.state, ::handleState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleState(state: CodeConfirmState) {
        when (state) {
            is CodeConfirmState.CodeConfirm -> {
                if (state.hasCodeError) {
                    binding.etCodeConform.setText("")
                }
            }
            CodeConfirmState.OpenRegistrationScreen -> {}
            CodeConfirmState.OpenProfileScreen -> {}
            CodeConfirmState.Error -> {
                binding.groupError.isVisible = true
                binding.groupSuccess.isVisible = false
            }
        }
    }

    companion object {
        const val PHONE_NUMBER = "phoneNumber"
    }
}
