package ru.zar1official.smartbathclient.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    contentColor: Color = Color.White,
    shape: Shape = CircleShape,
    strokeColor: Color = Color.Black,
    icon: Painter,
    contentDescription: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        elevation = ButtonDefaults.elevation(
            0.dp,
            0.dp
        ),
        contentPadding = PaddingValues(20.dp, 20.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )
    ) {
        Icon(painter = icon, contentDescription = "")
    }
}