package ru.zar1official.smartbathclient.presentation.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import ru.zar1official.smartbathclient.data.models.BathState
import ru.zar1official.smartbathclient.domain.usecases.*
import ru.zar1official.smartbathclient.domain.usecases.result.GetRequestResult
import ru.zar1official.smartbathclient.domain.usecases.result.PostRequestResult

class MainViewModel(
    private val readUIdUseCase: ReadUIdUseCase,
    private val changeTemperatureUseCase: ChangeTemperatureUseCase,
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

    private val _temperature = MutableLiveData<Float>()
    val temperature: LiveData<Float> = _temperature

    private val _waterColor = MutableLiveData<WaterColor>()
    val waterColor: LiveData<WaterColor> = _waterColor

    private val _drainStatus = MutableLiveData<Boolean>()
    val drainStatus: LiveData<Boolean> = _drainStatus

    private val _cranStatus = MutableLiveData<Boolean>()
    val cranStatus: LiveData<Boolean> = _cranStatus

    private var _uId: Long = 0L
    private var checkingWaterJob: Job? = null

    val event = Channel<MainScreenEvent>(Channel.BUFFERED)


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
            viewModelScope.launch {
                when (stopFetchingWaterUseCase.invoke(_uId)) {
                    PostRequestResult.Error -> showError()
                    PostRequestResult.Success -> _cranStatus.value = false
                }
            }
        }
    }

    private fun onStartCheckingWaterPercentage() {
        if (checkingWaterJob == null) {
            checkingWaterJob =
                viewModelScope.launch {
                    while (true) {
                        val result = readBathStateUseCase.invoke(_uId)
                        when (result) {
                            is GetRequestResult.NetworkError -> this.cancel()
                            is GetRequestResult.Success<BathState> -> {
                                val state = result.data
                                val percent = state.fillingProcent.toInt()
                                if (_percentage.value != percent) {
                                    _percentage.value = percent
                                }
                                delay(1000L)
                            }
                        }
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
                val result = readBathStateUseCase.invoke(id)
                when (result) {
                    is GetRequestResult.NetworkError -> showError()
                    is GetRequestResult.Success<BathState> -> {
                        val state = result.data
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

    fun onChangeTemperature(temperature: Float) {
        _temperature.value = temperature
    }

    fun onSaveTemperature(temperature: Float) {
        viewModelScope.launch {
            changeTemperatureUseCase.invoke(_uId, temperature)
        }
    }

    private fun showError() {
        event.trySend(MainScreenEvent.Error)
    }

}