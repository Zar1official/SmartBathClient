package ru.zar1official.smartbathclient.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.smartbathclient.domain.repository.Repository

class StopFetchingWaterUseCase(private val repository: Repository) {
    suspend fun invoke(uId: Long) = withContext(Dispatchers.IO) {
        repository.fetchingWater(uId, false)
    }
}