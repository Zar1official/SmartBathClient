package ru.zar1official.smartbathclient.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.smartbathclient.domain.repository.Repository

class ReadBathStateUseCase(private val repository: Repository) {
    suspend fun invoke() = withContext(Dispatchers.IO) {
        repository.readBathState()
    }
}