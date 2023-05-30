package com.greenhouses.presentation.screen.profile

data class ProfileDataModel(
    val profileModel: ProfileModel
)

data class ProfileModel(
    val id: Int,
    val name: String,
    val username: String,
    val birthday: String,
    val city: String,
    val avatar: String,
    val phone: String
)
