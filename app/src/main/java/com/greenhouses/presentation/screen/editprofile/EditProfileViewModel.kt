package com.greenhouses.presentation.screen.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenhouses.R
import com.greenhouses.domain.repository.AuthorizationRepository
import com.greenhouses.domain.repository.PreferenceManagerRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val authorizationRepository: AuthorizationRepository,
    private val preferenceManagerRepository: PreferenceManagerRepository
) : ViewModel() {

    private val _command = MutableSharedFlow<EditProfileCommand>()
    val command = _command.asSharedFlow()

    fun updateUserInfo(name: String, city: String, birthday: String) {
        viewModelScope.launch {
            val validName = name.isNotEmpty()
            val validCity = name.isNotEmpty()
            val validBirthday = name.isNotEmpty()
            if (validName && validCity && validBirthday) {
                runCatching {
                    authorizationRepository.updateUserInfo(
                        name = name,
                        city = city,
                        birthday = birthday,
                        login = preferenceManagerRepository.getLogin()
                    )
                }.onSuccess {
                    _command.emit(EditProfileCommand.OpenProfileScreen)
                }.onFailure {
                    _command.emit(EditProfileCommand.Error)
                }
            } else {
                _command.emit(
                    EditProfileCommand.ErrorValidationUiModel(
                        nameMessage = if (validName) null else R.string.registration_name_error,
                        cityMessage = if (validCity) null else R.string.registration_name_error,
                        birthdayMessage = if (validBirthday) null else R.string.registration_name_error
                    )
                )
            }
        }
    }
}
