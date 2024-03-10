package com.example.cab.activities.registration.model

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.cab.activities.registration.dataStore
import com.example.cab.activities.registration.model.StoreManager.Companion.PreferencesKeys.USER_NAME
import com.example.cab.activities.registration.model.StoreManager.Companion.PreferencesKeys.USER_PHONE
import com.example.cab.activities.registration.model.StoreManager.Companion.PreferencesKeys.USER_SURNAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class StoreManager {
    companion object {
        suspend fun saveUserData(
            context: Context,
            userData: UserData,
        ) {
            context.dataStore.edit { preferences ->
                preferences[USER_NAME] = userData.userName
                preferences[USER_SURNAME] = userData.userSurname
                preferences[USER_PHONE] = userData.userPhone
            }
        }

        fun readUserData(context: Context): Flow<UserData> {
            return context.dataStore.data
                .map { preferences ->
                    UserData(
                        userName = preferences[USER_NAME] ?: "",
                        userSurname = preferences[USER_SURNAME] ?: "",
                        userPhone = preferences[USER_PHONE] ?: ""
                    )
                }
                .catch {
                    context.dataStore.edit { preferences ->
                        preferences.clear()
                        saveUserData(context, defaultUser)
                    }
                }
        }

        object PreferencesKeys {
            val USER_NAME = stringPreferencesKey("name")
            val USER_SURNAME = stringPreferencesKey("surname")
            val USER_PHONE = stringPreferencesKey("phone")
        }

        private val defaultUser = UserData("","","")
    }
}