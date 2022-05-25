package com.example.mymoviedb.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.userDataStore by preferencesDataStore(
    name = UserDataStoreManager.DATASTORE_NAME
)
class UserDataStoreManager (private val context: Context){

    suspend fun setUser(emailValue: String, usernameValue: String){
        context.userDataStore.edit { preferences ->
            preferences[EMAIL_KEY] = emailValue
            preferences[USERNAME_KEY] = usernameValue
        }
    }

    fun getEmail(): Flow<String> {
        return context.userDataStore.data.map{ preferences ->
            preferences[EMAIL_KEY] ?: "-"
        }
    }

    fun getUsername(): Flow<String> {
        return context.userDataStore.data.map{ preferences ->
            preferences[USERNAME_KEY] ?: "-"
        }
    }

    suspend fun deleteUser(){
        context.userDataStore.edit {
            it.remove(EMAIL_KEY)
            it.remove(USERNAME_KEY)
        }
    }

    companion object {
        const val DATASTORE_NAME = "user_preferences"
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val USERNAME_KEY = stringPreferencesKey("username")
    }

}