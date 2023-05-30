package com.greenhouses.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.greenhouses.domain.repository.PreferenceManagerRepository
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

    override suspend fun getAccessToken(): String = accessToken.first()

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
}
