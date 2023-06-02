package com.greenhouses.data.repository

import com.greenhouses.data.GreenhousesApi
import com.greenhouses.data.mapper.mapToModel
import com.greenhouses.domain.repository.ProfileRepository
import com.greenhouses.presentation.screen.profile.ProfileDataModel
import com.greenhouses.presentation.screen.profile.ProfileModel
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val greenhousesApi: GreenhousesApi
) : ProfileRepository {

    override suspend fun getUserInfo(): ProfileDataModel =
        greenhousesApi.getProfile().mapToModel()
}
