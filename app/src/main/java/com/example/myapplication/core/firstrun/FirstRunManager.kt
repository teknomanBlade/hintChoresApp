package com.example.myapplication.core.firstrun

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "app_prefs")

class FirstRunManager(
    private val context: Context
) {

    private val FIRST_RUN_KEY = booleanPreferencesKey("first_run")

    suspend fun isFirstRun():Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[FIRST_RUN_KEY] ?: true
    }

    suspend fun setFirstRunCompleted() {
        context.dataStore.edit { prefs ->
            prefs[FIRST_RUN_KEY] = false
        }
    }
}