package com.greenhouses.presentation.screen.editprofile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.greenhouses.R
import com.greenhouses.databinding.FragmentEditProfileBinding
import com.greenhouses.di.ViewModelFactory
import com.greenhouses.extension.launchWhenStarted
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class EditProfileFragment : Fragment(), HasAndroidInjector {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var defaultViewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelFactory)[EditProfileViewModel::class.java]
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
        _binding = FragmentEditProfileBinding.inflate(layoutInflater)
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

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

    private fun handleCommand(command: EditProfileCommand) {
        when (command) {
            EditProfileCommand.OpenProfileScreen -> goBack()
            EditProfileCommand.Error -> {
                binding.groupError.isVisible = true
                binding.groupSuccess.isVisible = false
            }
            is EditProfileCommand.ErrorValidationUiModel -> {
                binding.tilName.error = command.nameMessage?.let(::getString)
                binding.tilCity.error = command.cityMessage?.let(::getString)
                binding.tilBirthday.error = command.birthdayMessage?.let(::getString)
            }
        }
    }

    private fun initUi() {
        with(binding) {
            btnRefresh.setOnClickListener {
                groupError.isVisible = false
                groupSuccess.isVisible = true
            }
            etName.doAfterTextChanged {
                tilName.error = null
            }
            etBirthday.doAfterTextChanged {
                tilBirthday.error = null
            }
            etCity.doAfterTextChanged {
                tilCity.error = null
            }
            toolBar.setNavigationOnClickListener { goBack() }
            toolBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.save -> {
                        viewModel.updateUserInfo(
                            name = etName.text.toString(),
                            city = etCity.text.toString(),
                            birthday = etBirthday.text.toString()
                        )
                        true
                    }
                    else -> false
                }
            }
        }
    }
}
