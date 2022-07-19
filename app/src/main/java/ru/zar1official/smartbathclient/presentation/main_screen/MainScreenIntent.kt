package ru.zar1official.smartbathclient.presentation.main_screen

import ru.zar1official.smartbathclient.domain.usecases.WaterColor

sealed class MainScreenIntent {
    object UpdateState : MainScreenIntent()
    object StartFetchWater : MainScreenIntent()
    object StopFetchWater : MainScreenIntent()
    object ChangeDrainStatus : MainScreenIntent()
    data class ChangeTemperature(val temperature: Float) : MainScreenIntent()
    object SaveTemperature : MainScreenIntent()
    data class ChangeWaterColor(val color: WaterColor) : MainScreenIntent()
}

