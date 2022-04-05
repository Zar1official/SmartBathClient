package ru.zar1official.smartbathclient.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.zar1official.smartbathclient.domain.usecases.*

class MainViewModel(
    private val readUIdUseCase: ReadUIdUseCase,
    private val increaseTemperatureUseCase: IncreaseTemperatureUseCase,
    private val decreaseTemperatureUseCase: DecreaseTemperatureUseCase,
    private val readBathStateUseCase: ReadBathStateUseCase,
    private val startFetchingWaterUseCase: StartFetchingWaterUseCase,
    private val stopFetchingWaterUseCase: StopFetchingWaterUseCase,
    private val changeWaterColorUseCase: ChangeWaterColorUseCase,
    private val closeDrainUseCase: CloseDrainUseCase,
    private val openDrainUseCase: OpenDrainUseCase
) : ViewModel() {
    private val _isLoaded = MutableLiveData<Boolean>()
    val isLoaded: LiveData<Boolean> = _isLoaded

    private val _percentage = MutableLiveData<Int>()
    val percentage: LiveData<Int> = _percentage

    private val _temperature = MutableLiveData<Int>()
    val temperature: LiveData<Int> = _temperature

    private val _waterColor = MutableLiveData<WaterColor>()
    val waterColor: LiveData<WaterColor> = _waterColor

    private val _drainStatus = MutableLiveData<Boolean>()
    val drainStatus: LiveData<Boolean> = _drainStatus

    private val _cranStatus = MutableLiveData<Boolean>()
    val cranStatus: LiveData<Boolean> = _cranStatus

    private var _uId: Long = 0L
    private var checkingWaterJob: Job? = null


    fun onStartFetchingWater() {
        if (_cranStatus.value != true) {
            onStartCheckingWaterPercentage()
            viewModelScope.launch {
                startFetchingWaterUseCase.invoke(_uId)
                _cranStatus.value = true
            }
        }
    }

    fun onStopFetchingWater() {
        if (_cranStatus.value != false) {
            onStartCheckingWaterPercentage()
            viewModelScope.launch {
                stopFetchingWaterUseCase.invoke(_uId)
                _cranStatus.value = false
            }
        }
    }

    private fun onStartCheckingWaterPercentage() {
        if (checkingWaterJob == null) {
            checkingWaterJob =
                viewModelScope.launch {
                    while (true) {
                        val state = readBathStateUseCase.invoke(_uId)
                        val percent = state.fillingProcent.toInt()
                        if (_percentage.value != percent) {
                            _percentage.value = percent
                        }
                        delay(1000L)
                    }
                }
        }
    }

    private fun onStopCheckingWaterPercentage() {
        if (checkingWaterJob != null) {
            checkingWaterJob?.cancelChildren()
            checkingWaterJob?.cancel()
            checkingWaterJob = null
        }
    }

    fun onChangeDrainStatus() {
        val drainValue = drainStatus.value ?: false
        onStartCheckingWaterPercentage()
        viewModelScope.launch {
            if (drainValue) {
                closeDrainUseCase.invoke(_uId)
            } else {
                openDrainUseCase.invoke(_uId)
            }
            _drainStatus.value = !drainValue
        }
    }

    fun onUpdateState() {
        if (_isLoaded.value != true) {
            viewModelScope.launch {
                val id = readUIdUseCase.invoke()
                _uId = id
                val state = readBathStateUseCase.invoke(id)
                _temperature.value = state.temp
                _percentage.value = state.fillingProcent.toInt()
                _waterColor.value = when (state.waterColor) {
                    0 -> WaterColor.Red
                    1 -> WaterColor.Normal
                    else -> WaterColor.Blue
                }
                _drainStatus.value = state.drainStatus
                _isLoaded.value = true
            }
        }
    }

    fun onChangeWaterColor(color: WaterColor) {
        if (_waterColor.value != color) {
            viewModelScope.launch {
                changeWaterColorUseCase.invoke(_uId, color)
                _waterColor.value = color
            }
        }
    }

}