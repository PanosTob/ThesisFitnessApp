package gr.dipae.thesisfitnessapp.framework.datastore

import android.system.Os.remove
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.squareup.moshi.Moshi
import gr.dipae.thesisfitnessapp.util.ext.fromJson
import gr.dipae.thesisfitnessapp.util.ext.toJson
import kotlinx.coroutines.flow.singleOrNull
import javax.inject.Inject

//Data store available Keys
object PreferencesKey {
}

class CustomPreferencesDataStore @Inject constructor(
    val dataStore: DataStore<Preferences>,
    val moshi: Moshi
) {

    suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }

    suspend fun clearKey(key: String) {
        dataStore.edit { remove(key) }
    }

    suspend inline fun <reified T> set(key: String, value: T?) {
        value?.let {
            try {
                val jsonValue = value.toJson(moshi)
                dataStore.edit {
                    it[stringPreferencesKey(key)] = jsonValue
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend inline fun <reified T : Any> get(key: String): T? {
        var value: T? = null
        try {
            val jsonValue = dataStore.data.singleOrNull()?.get(stringPreferencesKey(key))
            value = jsonValue?.fromJson(moshi, T::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return value
    }
}