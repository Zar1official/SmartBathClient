package ru.zar1official.smartbathclient.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.smartbathclient.data.storage.StorageConstants
import ru.zar1official.smartbathclient.domain.repository.Repository

class ReadUIdUseCase(private val repository: Repository) {
    suspend fun invoke(): Long = withContext(Dispatchers.IO) {
        var uId = repository.readPrefLongValue(key = StorageConstants.STORAGE_SETTINGS_UID)
        if (uId == null) {
            uId = generateUId()
            repository.insertLongInPrefs(key = StorageConstants.STORAGE_SETTINGS_UID, long = uId)
        }
        return@withContext uId
    }

    private fun generateUId(): Long = (1..Long.MAX_VALUE).random()
}