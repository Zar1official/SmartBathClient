package ru.zar1official.smartbathclient.domain.usecases

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.smartbathclient.Constants
import ru.zar1official.smartbathclient.domain.repository.Repository
import ru.zar1official.smartbathclient.domain.usecases.result.PostRequestResult

class DecreaseTemperatureUseCase(private val repository: Repository) {
    suspend operator fun invoke(temperature: Int): PostRequestResult = withContext(Dispatchers.IO) {
        Log.d("kfkf", temperature.toString())
        if (temperature > Constants.TEMPERATURE_MIN_VALUE) {
            val a = kotlin.runCatching {
                repository.decreaseTemperature()
            }.getOrNull()
            Log.d("ff", a.toString())
        }
        return@withContext PostRequestResult.DataError
    }
}
