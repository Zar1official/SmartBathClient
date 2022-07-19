package ru.zar1official.smartbathclient.presentation.main_screen

import ru.zar1official.smartbathclient.domain.usecases.WaterColor

data class MainScreenState(
    val isLoaded: Boolean = false,
    val percentage: Int = 0,
    val waterColor: WaterColor = WaterColor.Normal,
    val drainStatus: Boolean = false,
    val craneStatus: Boolean = false,
    val temperature: Float = 35f
)
