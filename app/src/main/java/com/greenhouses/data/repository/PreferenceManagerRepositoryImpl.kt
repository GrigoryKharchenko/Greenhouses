package com.greenhouses.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.greenhouses.domain.repository.PreferenceManagerRepository
import com.greenhouses.presentation.model.UserInfoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataStore")

class PreferenceManagerRepositoryImpl @Inject constructor(
    private val context: Context
) : PreferenceManagerRepository {

    private val isAuthorizedKey = booleanPreferencesKey("is_authorized_key")

    private val isAuthorized = context.dataStore.data.map { pref ->
        pref[isAuthorizedKey] ?: false
    }

    private val accessTokenKey = stringPreferencesKey("access_token_key")

    private val accessToken = context.dataStore.data.map { pref ->
        pref[accessTokenKey] ?: ""
    }

    private val refreshTokenKey = stringPreferencesKey("refresh_token_key")

    private val refreshToken = context.dataStore.data.map { pref ->
        pref[refreshTokenKey] ?: ""
    }

    private val userInfoKey = stringPreferencesKey("user_info_key")

    private val userInfo = context.dataStore.data.map { pref ->
        pref[userInfoKey]
    }

    override suspend fun clear() {
        context.dataStore.edit { pref ->
            pref.clear()
        }
    }

    override suspend fun setAuthorized(isAuthorized: Boolean) {
        context.dataStore.edit { pref ->
            pref[isAuthorizedKey] = isAuthorized
        }
    }

    override suspend fun isAuthorized(): Boolean = isAuthorized.first()

    override suspend fun subscribeToGetAccessToken(): Flow<String> = accessToken

    override suspend fun setAccessToken(accessToken: String) {
        context.dataStore.edit { pref ->
            pref[accessTokenKey] = accessToken
        }
    }

    override suspend fun getRefreshToken(): String = refreshToken.first()

    override suspend fun setRefreshToken(refreshToken: String) {
        context.dataStore.edit { pref ->
            pref[refreshTokenKey] = refreshToken
        }
    }

    override suspend fun getUserInfo(): UserInfoModel {
        return getUserInfoFromJson(userInfo.firstOrNull())
    }

    override suspend fun subscribeToGetUserInfo(): Flow<UserInfoModel> {
        return userInfo.map { userInfoJson ->
            getUserInfoFromJson(userInfoJson)
        }
    }

    override suspend fun setDataUser(
        phone: String,
        name: String,
        login: String,
        refreshToken: String,
        accessToken: String
    ) {
        val userJson = UserInfoModel(
            name = name,
            phone = phone,
            login = login
        )
        context.dataStore.edit { prefs ->
            prefs[userInfoKey] = convertUserInfoToGson(userJson)
            prefs[refreshTokenKey] = refreshToken
            prefs[accessTokenKey] = accessToken
        }
    }

    override suspend fun setDataUser(userInfoModel: UserInfoModel) {
        context.dataStore.edit { prefs ->
            val updateUserInfo = prefs[userInfoKey]?.let {
                val savedUserInfo = getUserInfoFromJson(it)
                userInfoModel.copy(zodiac = savedUserInfo.zodiac)
            } ?: userInfoModel
            prefs[userInfoKey] = convertUserInfoToGson(updateUserInfo)
        }
    }

    override suspend fun updateDataUserInfo(
        name: String,
        birthday: String,
        city: String,
        photoBase64: String,
        zodiac: Int
    ) {
        val userInfo = getUserInfoFromJson(userInfo.first()).copy(
            name = name,
            birthday = birthday,
            city = city,
            avatar = photoBase64,
            zodiac = zodiac
        )
        context.dataStore.edit { prefs ->
            prefs[userInfoKey] = convertUserInfoToGson(userInfo)
        }
    }

    private fun convertUserInfoToGson(userInfo: UserInfoModel): String {
        return Gson().toJson(userInfo)
    }

    private fun getUserInfoFromJson(userInfoJson: String?): UserInfoModel {
        return if (userInfoJson.isNullOrEmpty()) {
            UserInfoModel()
        } else {
            Gson().fromJson(userInfoJson, UserInfoModel::class.java)
        }
    }
}
