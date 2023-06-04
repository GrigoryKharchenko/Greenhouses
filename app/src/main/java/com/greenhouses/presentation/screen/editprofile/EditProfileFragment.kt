package com.greenhouses.presentation.screen.editprofile

import android.Manifest
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.greenhouses.R
import com.greenhouses.databinding.FragmentEditProfileBinding
import com.greenhouses.di.ViewModelFactory
import com.greenhouses.extension.launchWhenStarted
import com.greenhouses.presentation.base.BaseFragment
import java.util.Calendar
import javax.inject.Inject

class EditProfileFragment : BaseFragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var defaultViewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelFactory)[EditProfileViewModel::class.java]
    }

    private val previewImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
            viewModel.perform(EditProfileEvent.SetAvatar(bitmap))
        }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery()
            }
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
        launchWhenStarted(viewModel.state, ::handleState)
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
        }
    }

    private fun handleState(state: EditProfileState) {
        with(binding) {
            tilName.error = state.nameMessage?.let(::getString)
            tilCity.error = state.cityMessage?.let(::getString)
            tilBirthday.error = state.birthdayMessage?.let(::getString)
            groupError.isVisible = state.isError
            groupSuccess.isVisible = state.isSuccess
            etCity.setText(state.city)
            etBirthday.setText(state.birthday)
            etName.setText(state.name)
            ivAvatar.setImageBitmap(state.avatar)
            flProgress.isVisible = state.isLoading
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
            ivAvatar.setOnClickListener {
                openGallery()
            }
            etBirthday.setOnClickListener {
                openDatePicker()
            }
            toolBar.setNavigationOnClickListener { goBack() }
            toolBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.save -> {
                        viewModel.perform(
                            EditProfileEvent.UpdateUserInfo(
                                name = etName.text.toString(),
                                city = etCity.text.toString(),
                                birthday = etBirthday.text.toString()
                            )
                        )
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, day ->
            binding.etBirthday.setText("$year-$month-$day")
        }, year, month, day).show()
    }

    private fun hasGalleryPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun openGallery() {
        if (hasGalleryPermission()) {
            previewImage.launch("image/*")
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}
