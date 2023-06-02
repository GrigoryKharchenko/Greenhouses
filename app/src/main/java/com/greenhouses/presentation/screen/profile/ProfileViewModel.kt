package com.greenhouses.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenhouses.domain.interactor.AuthorizationInteractor
import com.greenhouses.domain.manager.UserManager
import com.greenhouses.domain.repository.PreferenceManagerRepository
import com.greenhouses.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val userManager: UserManager,
) : ViewModel() {

    private val _userInfo = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val userInfo = _userInfo.asStateFlow()

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            runCatching {
                profileRepository.getUserInfo()
            }.onSuccess { profile ->
                _userInfo.emit(ProfileState.Success(profile))
            }.onFailure {
                _userInfo.emit(ProfileState.Error)
            }
        }
    }

    fun logUot() {
        viewModelScope.launch {
            userManager.logOut()
        }
    }
}
