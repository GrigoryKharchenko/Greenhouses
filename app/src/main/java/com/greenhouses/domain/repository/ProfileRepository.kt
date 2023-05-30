package com.greenhouses.domain.repository

import com.greenhouses.presentation.screen.profile.ProfileDataModel

interface ProfileRepository {

    suspend fun getUserInfo(): ProfileDataModel
}
