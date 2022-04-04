package ru.zar1official.smartbathclient

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import ru.zar1official.smartbathclient.presentation.MainViewModel
import ru.zar1official.smartbathclient.presentation.components.CustomButton
import ru.zar1official.smartbathclient.presentation.components.CustomProgress
import ru.zar1official.smartbathclient.ui.theme.DarkGreen

@Composable
fun MainScreen(viewModel: MainViewModel = getViewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val percentage = viewModel.percentage.observeAsState(initial = 0)
    val temperature = viewModel.temperature.observeAsState(initial = 0)

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .height(300.dp)
                .background(color = Color.White),
            shape = RoundedCornerShape(10.dp),
            elevation = 3.dp
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(contentAlignment = Alignment.Center) {
                    CustomProgress(
                        inactiveBarColor = Color.Gray,
                        activeBarColor = Color.Blue,
                        modifier = Modifier.size(200.dp),
                        strokeWidth = 10.dp,
                        percentage = percentage
                    )

                    Text(
                        text = "${percentage.value}%",
                        fontSize = MaterialTheme.typography.h5.fontSize,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Row {
                    CustomButton(
                        backgroundColor = DarkGreen,
                        icon = painterResource(id = R.drawable.ic_turn_off),
                        contentDescription = "",
                        contentPaddingValues = PaddingValues(20.dp),
                        onClick = { viewModel.onStartFetchingWater() }
                    )

                    Spacer(modifier = Modifier.width(45.dp))

                    CustomButton(
                        backgroundColor = Color.Red,
                        icon = painterResource(id = R.drawable.ic_turn_off),
                        contentDescription = "",
                        contentPaddingValues = PaddingValues(20.dp),
                        onClick = { viewModel.onStopFetchingWater() }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .height(100.dp)
                .background(color = Color.White),
            shape = RoundedCornerShape(10.dp),
            elevation = 3.dp
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomButton(
                    backgroundColor = Color.White,
                    contentColor = Color.DarkGray,
                    icon = painterResource(id = R.drawable.ic_decrease_temperature),
                    contentDescription = "",
                    contentPaddingValues = PaddingValues(10.dp, 10.dp),
                    borderStroke = BorderStroke(2.dp, Color.DarkGray),
                    onClick = { viewModel.onDecreaseTemperature() }
                )
                Text(
                    text = "${temperature.value}℃",
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.SemiBold,
                )

                CustomButton(
                    backgroundColor = Color.White,
                    contentColor = Color.DarkGray,
                    icon = painterResource(id = R.drawable.ic_increase_temperature),
                    contentDescription = "",
                    contentPaddingValues = PaddingValues(10.dp),
                    borderStroke = BorderStroke(2.dp, Color.DarkGray),
                    onClick = { viewModel.onIncreaseTemperature() }
                )
            }
        }
    }
}

