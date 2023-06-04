package com.greenhouses.presentation.screen.editprofile

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenhouses.R
import com.greenhouses.data.request.AvatarRequest
import com.greenhouses.data.request.UserUpdatedRequest
import com.greenhouses.domain.interactor.UserInteractor
import com.greenhouses.domain.service.ZodiacService
import com.greenhouses.presentation.util.decodeToBitmap
import com.greenhouses.presentation.util.encodeToBase64
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val userInteractor: UserInteractor,
    private val zodiacService: ZodiacService,
) : ViewModel() {

    private val _command = MutableSharedFlow<EditProfileCommand>()
    val command = _command.asSharedFlow()

    private val _state = MutableStateFlow(EditProfileState())
    val state = _state.asStateFlow()

    private var photoBase64 = ""

    init {
        getUser()
    }

    fun perform(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.SetAvatar -> {
                setAvatar(event.bitmap)
            }
            is EditProfileEvent.UpdateUserInfo -> {
                updateUserInfo(
                    name = event.name,
                    city = event.city,
                    birthday = event.birthday
                )
            }
        }
    }

    private fun setAvatar(bitmap: Bitmap) {
        photoBase64 = bitmap.encodeToBase64()
        _state.update {
            it.copy(
                avatar = bitmap
            )
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            val userInfo = userInteractor.getUserInfo()
            photoBase64 = userInfo.avatar
            _state.update {
                it.copy(
                    city = userInfo.city,
                    name = userInfo.name,
                    birthday = userInfo.birthday,
                    avatar = userInfo.avatar.decodeToBitmap(),
                    isSuccess = true
                )
            }
        }
    }

    private fun updateUserInfo(name: String, city: String, birthday: String) {
        viewModelScope.launch {
            val validName = name.isNotEmpty()
            val validCity = name.isNotEmpty()
            val validBirthday = name.isNotEmpty()
            if (validName && validCity && validBirthday) {
                _state.update {
                    it.copy(isLoading = true)
                }
                val login = userInteractor.getUserInfo().login
                runCatching {
                    userInteractor.updateUserInfo(
                        UserUpdatedRequest(
                            name = name,
                            city = city,
                            birthday = birthday,
                            username = login,
                            avatarRequest = AvatarRequest("", photoBase64),
                        )
                    )
                }.onSuccess {
                    val day = birthday.substring(startIndex = 8, endIndex = 10).toInt()
                    val month = birthday.substring(startIndex = 5, endIndex = 7).toInt()
                    userInteractor.updateDataUserInfo(
                        name = name,
                        city = city,
                        birthday = birthday,
                        photoBase64 = photoBase64,
                        zodiac = zodiacService.checkZodiac(day, month)
                    )
                    _command.emit(EditProfileCommand.OpenProfileScreen)
                }.onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isError = true,
                            isSuccess = false
                        )
                    }
                }
            } else {
                _state.update {
                    it.copy(
                        nameMessage = if (validName) null else R.string.registration_name_error,
                        cityMessage = if (validCity) null else R.string.registration_name_error,
                        birthdayMessage = if (validBirthday) null else R.string.registration_name_error
                    )
                }
            }
        }
    }
}
