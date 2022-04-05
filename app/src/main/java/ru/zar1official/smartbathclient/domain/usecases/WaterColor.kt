package ru.zar1official.smartbathclient.domain.usecases

import androidx.compose.ui.graphics.Color

sealed class WaterColor(val color: Color) {
    object Blue : WaterColor(color = Color.Blue)
    object Red : WaterColor(color = Color.Red)
    object Normal : WaterColor(color = Color.Gray)
}
