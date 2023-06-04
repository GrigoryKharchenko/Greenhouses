package com.greenhouses.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenhouses.data.mapper.mapToProfileModel
import com.greenhouses.data.mapper.mapToUserInfoModel
import com.greenhouses.domain.manager.UserManager
import com.greenhouses.domain.repository.PreferenceManagerRepository
import com.greenhouses.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userManager: UserManager,
    private val preferenceManagerRepository: PreferenceManagerRepository
) : ViewModel() {

    private val _userInfo = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val userInfo = _userInfo.asStateFlow()

    private val _command = MutableSharedFlow<ProfileCommand>()
    val command = _command.asSharedFlow()

    init {
        getUserInfo()
        subscribeToUserInfo()
    }

    fun perform(event: ProfileEvent) {
        when (event) {
            ProfileEvent.Logout -> logUot()
        }
    }

    private fun logUot() {
        viewModelScope.launch {
            userManager.logOut()
            _command.emit(ProfileCommand.OpenAuthorization)
        }
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            runCatching {
                userRepository.getUserInfo()
            }.onSuccess { profile ->
                preferenceManagerRepository.setDataUser(
                    profile.mapToUserInfoModel()
                )
            }.onFailure {
                _userInfo.emit(ProfileState.Error)
            }
        }
    }

    private fun subscribeToUserInfo() {
        viewModelScope.launch {
            preferenceManagerRepository.subscribeToGetUserInfo()
                .onEach { userInfo ->
                    _userInfo.emit(ProfileState.Success(userInfo.mapToProfileModel()))
                }.collect()
        }
    }
}
