package ru.zar1official.smartbathclient.presentation.main_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
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

    private var _uId: Long = 0L
    private var checkingWaterJob: Job? = null

    var screenState by mutableStateOf(MainScreenState())
        private set

    private val _event = MutableSharedFlow<MainScreenEvent>()
    val event = _event.asSharedFlow()

    private fun onStartCheckingWaterPercentage() {
        if (checkingWaterJob == null) {
            checkingWaterJob =
                viewModelScope.launch {
                    while (true) {
                        when (val result = readBathStateUseCase.invoke(_uId)) {
                            is GetRequestResult.NetworkError -> onStopCheckingWaterPercentage()
                            is GetRequestResult.Success<BathState> -> {
                                val state = result.data
                                val percent = state.fillingProcent.toInt()
                                if (screenState.percentage != percent) {
                                    screenState = screenState.copy(percentage = percent)
                                }
                                delay(1000L)
                            }
                        }
                    }
                }
        }
    }

    fun onSendIntent(intent: MainScreenIntent) {
        when (intent) {
            is MainScreenIntent.ChangeDrainStatus -> {
                val drainValue = screenState.drainStatus

                viewModelScope.launch {
                    val result = if (drainValue) {
                        closeDrainUseCase.invoke(_uId)
                    } else {
                        openDrainUseCase.invoke(_uId)
                    }
                    when (result) {
                        PostRequestResult.Error -> showError()
                        PostRequestResult.Success -> {
                            screenState = screenState.copy(drainStatus = !drainValue)
                        }
                    }
                }
            }

            is MainScreenIntent.ChangeWaterColor -> {
                val color = intent.color

                if (screenState.waterColor != color) {
                    viewModelScope.launch {
                        when (changeWaterColorUseCase.invoke(_uId, color)) {
                            PostRequestResult.Error -> showError()
                            PostRequestResult.Success -> screenState =
                                screenState.copy(waterColor = color)
                        }
                    }
                }
            }

            is MainScreenIntent.SaveTemperature -> {
                viewModelScope.launch {
                    if (changeTemperatureUseCase.invoke(
                            _uId,
                            screenState.temperature
                        ) == PostRequestResult.Error
                    ) {
                        showError()
                    }
                }
            }

            is MainScreenIntent.ChangeTemperature -> {
                screenState = screenState.copy(temperature = intent.temperature)
            }

            is MainScreenIntent.StartFetchWater -> {
                if (!screenState.craneStatus) {
                    viewModelScope.launch {
                        when (startFetchingWaterUseCase.invoke(_uId)) {
                            PostRequestResult.Error -> showError()
                            PostRequestResult.Success -> screenState =
                                screenState.copy(craneStatus = true)
                        }
                    }
                }
            }

            is MainScreenIntent.StopFetchWater -> {
                if (screenState.craneStatus) {
                    viewModelScope.launch {
                        when (stopFetchingWaterUseCase.invoke(_uId)) {
                            PostRequestResult.Error -> showError()
                            PostRequestResult.Success -> screenState =
                                screenState.copy(craneStatus = false)
                        }
                    }
                }
            }

            is MainScreenIntent.UpdateState -> {
                if (!screenState.isLoaded) {
                    viewModelScope.launch {
                        val id = readUIdUseCase.invoke()
                        _uId = id
                        when (val result = readBathStateUseCase.invoke(id)) {
                            is GetRequestResult.NetworkError -> showLoadingError()
                            is GetRequestResult.Success<BathState> -> {
                                val state = result.data
                                screenState = screenState.copy(
                                    isLoaded = true,
                                    craneStatus = state.craneActie,
                                    drainStatus = state.drainStatus,
                                    percentage = state.fillingProcent.toInt(),
                                    waterColor = when (state.waterColor) {
                                        0 -> WaterColor.Red
                                        1 -> WaterColor.Normal
                                        else -> WaterColor.Blue
                                    },
                                    temperature = state.temp
                                )
                                onStartCheckingWaterPercentage()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onStopCheckingWaterPercentage() {
        checkingWaterJob?.cancelChildren()
        checkingWaterJob?.cancel()
        checkingWaterJob = null
    }

    private suspend fun showError() {
        _event.emit(MainScreenEvent.Error)
    }

    private suspend fun showLoadingError() {
        _event.emit(MainScreenEvent.LoadingError)
    }
}