package ru.zar1official.smartbathclient.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.smartbathclient.domain.repository.Repository
import ru.zar1official.smartbathclient.domain.usecases.result.PostRequestResult

class CloseDrainUseCase(private val repository: Repository) {
    suspend fun invoke(uId: Long): PostRequestResult = withContext(Dispatchers.IO) {
        kotlin.runCatching { repository.changeDrainsStatus(uId = uId, drain = false) }.onFailure {
            return@withContext PostRequestResult.Error
        }
        return@withContext PostRequestResult.Success
    }
}