package ru.zar1official.smartbathclient.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.first
import ru.zar1official.smartbathclient.data.models.BathState
import ru.zar1official.smartbathclient.data.network.Service
import ru.zar1official.smartbathclient.domain.repository.Repository

class RepositoryImpl(private val service: Service, private val dataStore: DataStore<Preferences>) :
    Repository {

    override suspend fun readPrefLongValue(key: String): Long? {
        val prefs = dataStore.data.first()
        val dataStoreKey = longPreferencesKey(name = key)
        return prefs[dataStoreKey]
    }

    override suspend fun fetchingWater(uId: Long, fetch: Boolean) {
        service.fetchingWater(uId = uId, fetch = fetch)
    }


    override suspend fun insertLongInPrefs(key: String, long: Long) {
        dataStore.edit { settings ->
            val dataStoreKey = longPreferencesKey(name = key)
            settings[dataStoreKey] = long
        }
    }

    override suspend fun readBathState(uId: Long): BathState {
        return service.readBathState(uId = uId)
    }

    override suspend fun increaseTemperature() {

    }

    override suspend fun decreaseTemperature() {

    }

    override suspend fun readTemperature(): Int {
        return listOf(1, 2, 4, 56, 7, 88).random()
    }
}