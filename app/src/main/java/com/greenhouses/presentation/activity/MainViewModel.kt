package com.greenhouses.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenhouses.domain.manager.UserManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel() {

    private val _command = MutableSharedFlow<CommandActivity>()
    val command = _command.asSharedFlow()

    init {
        isAuthorized()
    }

    private fun isAuthorized() {
        viewModelScope.launch {
            if (userManager.isAuthorized()) {
                _command.emit(CommandActivity.OpenProfileScreen)
            } else {
                _command.emit(CommandActivity.OpenAuthorizationScreen)
            }
        }
    }
}
