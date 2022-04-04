package ru.zar1official.smartbathclient.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.zar1official.smartbathclient.Constants
import ru.zar1official.smartbathclient.domain.usecases.DecreaseTemperatureUseCase
import ru.zar1official.smartbathclient.domain.usecases.IncreaseTemperatureUseCase
import ru.zar1official.smartbathclient.domain.usecases.ReadTemperatureUseCase
import ru.zar1official.smartbathclient.domain.usecases.result.PostRequestResult

class MainViewModel(
    private val increaseTemperatureUseCase: IncreaseTemperatureUseCase,
    private val decreaseTemperatureUseCase: DecreaseTemperatureUseCase,
    private val readTemperatureUseCase: ReadTemperatureUseCase
) : ViewModel() {
    private val _percentage = MutableLiveData<Int>().apply { value = 0 }
    val percentage: LiveData<Int> = _percentage

    private val _temperature = MutableLiveData<Int>().apply { value = 0 }
    val temperature: LiveData<Int> = _temperature

    fun onChangePercentage() {
        val value = percentage.value!!
        _percentage.value = value + 1
    }

    var job: Job? = null


    fun onStartFetchingWater() {
        job = viewModelScope.launch {
            repeat(100) {
                delay(1000L)
                onChangePercentage()
            }
        }
    }

    fun onStopFetchingWater() {
        job?.cancel()
    }

    fun onIncreaseTemperature() {
        viewModelScope.launch {
            val temp = temperature.value ?: Constants.TEMPERATURE_DEFAULT_VALUE
            when (increaseTemperatureUseCase.invoke(temp)) {
                is PostRequestResult.NetworkError -> {

                }
                is PostRequestResult.Success -> {
                    _temperature.value =
                        _temperature.value ?: Constants.TEMPERATURE_DEFAULT_VALUE + 1
                }
                PostRequestResult.DataError -> {

                }
            }
        }
    }

    fun onDecreaseTemperature() {
        viewModelScope.launch {
            val temp = temperature.value ?: Constants.TEMPERATURE_DEFAULT_VALUE
            when (decreaseTemperatureUseCase.invoke(temp)) {
                is PostRequestResult.NetworkError -> {

                }
                is PostRequestResult.Success -> {
                    _temperature.value =
                        _temperature.value ?: Constants.TEMPERATURE_DEFAULT_VALUE - 1
                }
                PostRequestResult.DataError -> {

                }
            }
        }
    }


}