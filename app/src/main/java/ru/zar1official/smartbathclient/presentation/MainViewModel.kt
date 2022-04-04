package ru.zar1official.smartbathclient.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import ru.zar1official.smartbathclient.Constants
import ru.zar1official.smartbathclient.domain.usecases.*
import ru.zar1official.smartbathclient.domain.usecases.result.PostRequestResult

class MainViewModel(
    private val readUIdUseCase: ReadUIdUseCase,
    private val increaseTemperatureUseCase: IncreaseTemperatureUseCase,
    private val decreaseTemperatureUseCase: DecreaseTemperatureUseCase,
    private val readTemperatureUseCase: ReadTemperatureUseCase,
    private val readBathStateUseCase: ReadBathStateUseCase,
    private val startFetchingWaterUseCase: StartFetchingWaterUseCase,
    private val stopFetchingWaterUseCase: StopFetchingWaterUseCase
) : ViewModel() {
    private val _isLoaded = MutableLiveData<Boolean>()
    val isLoaded: LiveData<Boolean> = _isLoaded

    private val _percentage = MutableLiveData<Int>()
    val percentage: LiveData<Int> = _percentage

    private val _temperature = MutableLiveData<Int>()
    val temperature: LiveData<Int> = _temperature

    private val _waterColor = MutableLiveData<Int>()
    val waterColor: LiveData<Int> = _waterColor

    private val _drainStatus = MutableLiveData<Boolean>()
    val drainStatus: LiveData<Boolean> = _drainStatus

    fun onChangePercentage() {
        val value = percentage.value!!
        _percentage.value = value + 1
    }

    private var _uId: Long = 0L


    fun onStartFetchingWater() {
        if (checkingWaterJob == null) {
            checkingWaterJob =
                viewModelScope.launch {
                    startFetchingWaterUseCase.invoke(_uId)
                    while (true) {
                        val state = readBathStateUseCase.invoke(_uId)
                        val percent = state.fillingProcent.toInt()
                        if (_percentage.value != percent) {
                            _percentage.value = percent
                        }
                    }
                }
        }
    }

    fun onStopFetchingWater() {
        if (checkingWaterJob != null) {
            checkingWaterJob?.cancelChildren()
            checkingWaterJob?.cancel()
            checkingWaterJob = null
            viewModelScope.launch {
                stopFetchingWaterUseCase.invoke(_uId)
            }
        }
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

    private var checkingWaterJob: Job? = null

    fun onUpdateState() {
        viewModelScope.launch {
            val id = readUIdUseCase.invoke()
            _uId = id
            val state = readBathStateUseCase.invoke(id)
            _temperature.value = state.temp
            _percentage.value = state.fillingProcent.toInt()
            _waterColor.value = state.waterColor
            _drainStatus.value = state.drainStatus
            _isLoaded.value = true
        }
    }


}