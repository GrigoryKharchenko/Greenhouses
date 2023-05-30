package com.greenhouses.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.greenhouses.domain.repository.PreferenceManagerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataStore")

class PreferenceManagerRepositoryImpl @Inject constructor(
    private val context: Context
) : PreferenceManagerRepository {

    private val accessTokenKey = stringPreferencesKey("access_token_key")

    private val accessToken = context.dataStore.data.map { token ->
        token[accessTokenKey] ?: ""
    }

    private val refreshTokenKey = stringPreferencesKey("refresh_token_key")

    private val refreshToken = context.dataStore.data.map { token ->
        token[refreshTokenKey] ?: ""
    }

    private val phoneKey = stringPreferencesKey("phone_key")

    private val phone = context.dataStore.data.map { phone ->
        phone[phoneKey] ?: ""
    }

    private val nameKey = stringPreferencesKey("name_key")

    private val name = context.dataStore.data.map { name ->
        name[nameKey] ?: ""
    }

    private val loginKey = stringPreferencesKey("login_key")

    private val login = context.dataStore.data.map { login ->
        login[loginKey] ?: ""
    }

    override suspend fun getAccessToken(): Flow<String> = accessToken

    override suspend fun setAccessToken(accessToken: String) {
        context.dataStore.edit { token ->
            token[accessTokenKey] = accessToken
        }
    }

    override suspend fun getRefreshToken(): String = refreshToken.first()

    override suspend fun setRefreshToken(refreshToken: String) {
        context.dataStore.edit { token ->
            token[refreshTokenKey] = refreshToken
        }
    }

    override suspend fun setDataUser(
        phone: String,
        name: String,
        login: String,
        refreshToken: String,
        accessToken: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[nameKey] = name
            prefs[phoneKey] = phone
            prefs[loginKey] = login
            prefs[refreshTokenKey] = refreshToken
            prefs[accessTokenKey] = accessToken
        }
    }

    override suspend fun getPhone(): String = phone.first()

    override suspend fun getName(): String = name.first()

    override suspend fun getLogin(): String = login.first()
}
