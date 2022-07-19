package ru.zar1official.smartbathclient.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import ru.zar1official.smartbathclient.domain.usecases.WaterColor

@Composable
fun ButtonGroup(
    modifier: Modifier = Modifier,
    buttons: List<WaterColor>,
    onChangeColor: (button: WaterColor) -> Unit,
    selectedButton: WaterColor,
    horizontalArrangement: Arrangement.Horizontal,
    verticalAlignment: Alignment.Vertical,
    contentPaddingValues: PaddingValues,
    borderStrokeColor: Color,
    borderStrokeWidth: Dp
) {
    Row(
        horizontalArrangement = horizontalArrangement,
        modifier = modifier,
        verticalAlignment = verticalAlignment
    ) {
        buttons.forEach { button ->
            CustomButton(
                backgroundColor = button.color,
                contentDescription = "",
                contentPaddingValues = contentPaddingValues,
                onClick = {
                    onChangeColor.invoke(button)
                },
                borderStroke = if (selectedButton == button) BorderStroke(
                    borderStrokeWidth,
                    borderStrokeColor
                ) else null
            )
        }
    }
}
