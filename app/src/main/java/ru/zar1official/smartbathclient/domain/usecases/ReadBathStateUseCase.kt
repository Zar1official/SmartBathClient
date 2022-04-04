package ru.zar1official.smartbathclient.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.smartbathclient.data.models.BathState
import ru.zar1official.smartbathclient.domain.repository.Repository

class ReadBathStateUseCase(private val repository: Repository) {
    suspend fun invoke(uId: Long): BathState = withContext(Dispatchers.IO) {
        repository.readBathState(uId = uId)
    }
}