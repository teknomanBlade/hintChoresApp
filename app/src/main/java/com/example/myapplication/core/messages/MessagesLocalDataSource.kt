package com.example.myapplication.core.messages

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapplication.model.data.entities.ReminderMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.dataStore by preferencesDataStore("messages_store")

class MessagesLocalDataSource(
    private val context: Context
) {

    private val key = stringPreferencesKey("messages_json")
    private val SELECTED_MESSAGE_KEY = stringPreferencesKey("selected_message")
    suspend fun getMessages(): List<ReminderMessage> {
        val prefs = context.dataStore.data.first()
        val json = prefs[key] ?: return emptyList()
        return Json.decodeFromString(json)
    }

    suspend fun saveMessages(messages: List<ReminderMessage>) {
        context.dataStore.edit { prefs ->
            prefs[key] = Json.encodeToString(messages)
        }
    }

    suspend fun saveSelectedMessage(text: String) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_MESSAGE_KEY] = text
        }
    }

    suspend fun deleteSelectedMessage(){
        context.dataStore.edit { it.remove(SELECTED_MESSAGE_KEY) }
    }
    // Función para recuperar (como Flow)
    fun getSelectedMessage(): Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[SELECTED_MESSAGE_KEY]
    }
}