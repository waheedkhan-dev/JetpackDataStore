package com.codecollapse.jetpackdatastore.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepository @Inject constructor(private val context: Context){

    object PreferencesKey {
        val counter = intPreferencesKey("counter_key")
    }

    private val Context.dataStore by preferencesDataStore(name = "counter")

    suspend fun incrementCounter() {
        context.dataStore.edit { preferences->
            // Read the current value from preferences
            val currentCounterValue = preferences[PreferencesKey.counter] ?: 0
            // Write the current value + 1 into the preferences
            preferences[PreferencesKey.counter] = currentCounterValue + 1
        }
    }

    suspend fun decrementCounter() {
        context.dataStore.edit { preferences->
            // Read the current value from preferences
            val currentCounterValue = preferences[PreferencesKey.counter] ?: 0
            // Write the current value - 1 into the preferences
            preferences[PreferencesKey.counter] = currentCounterValue - 1
        }
    }

    suspend fun setCounter(counterValue: Int){
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.counter] = counterValue
        }
    }

    val getCount : Flow<Int> = context.dataStore.data
        .catch {
            if(this is Exception){
                emit(emptyPreferences())
            }
        }.map { preference->
            val name = preference[PreferencesKey.counter] ?: 0
            name
        }
}