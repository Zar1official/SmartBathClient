package ru.zar1official.smartbathclient.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.smartbathclient.domain.repository.Repository

class ChangeWaterColorUseCase(private val repository: Repository) {
    suspend fun invoke(uId: Long, color: WaterColor) = withContext(Dispatchers.IO) {
        val colorValue = when (color) {
            WaterColor.Red -> 0
            WaterColor.Normal -> 1
            WaterColor.Blue -> 2
        }
        repository.changeWaterColor(uId = uId, color = colorValue)
    }
}