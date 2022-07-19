package ru.zar1official.smartbathclient.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.smartbathclient.data.models.BathState
import ru.zar1official.smartbathclient.domain.repository.Repository
import ru.zar1official.smartbathclient.domain.usecases.result.GetRequestResult

class ReadBathStateUseCase(private val repository: Repository) {
    suspend operator fun invoke(uId: Long): GetRequestResult<BathState> =
        withContext(Dispatchers.IO) {
            val data = kotlin.runCatching { repository.readBathState(uId = uId) }.getOrNull()
            if (data != null) {
                return@withContext GetRequestResult.Success<BathState>(data = data)
            }
            return@withContext GetRequestResult.NetworkError
        }
}