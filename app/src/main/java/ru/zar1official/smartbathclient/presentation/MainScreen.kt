package ru.zar1official.smartbathclient

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import ru.zar1official.smartbathclient.domain.usecases.WaterColor
import ru.zar1official.smartbathclient.presentation.MainViewModel
import ru.zar1official.smartbathclient.presentation.components.ButtonGroup
import ru.zar1official.smartbathclient.presentation.components.CustomButton
import ru.zar1official.smartbathclient.presentation.components.CustomProgress
import ru.zar1official.smartbathclient.ui.theme.DarkGreen

@Composable
fun MainScreen(viewModel: MainViewModel = getViewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val isLoaded = viewModel.isLoaded.observeAsState(initial = false)
    val percentage = viewModel.percentage.observeAsState(initial = 0)
    val temperature = viewModel.temperature.observeAsState(initial = 0)
    val selectedColor = viewModel.waterColor.observeAsState(initial = WaterColor.Normal)
    val drainStatus = viewModel.drainStatus.observeAsState(initial = false)
    val craneStatus = viewModel.cranStatus.observeAsState(initial = false)

    if (isLoaded.value) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .height(300.dp)
                    .background(color = Color.White),
                shape = RoundedCornerShape(10.dp),
                elevation = 2.dp
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

            Spacer(modifier = Modifier.height(25.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .height(100.dp)
                    .background(color = Color.White),
                shape = RoundedCornerShape(10.dp),
                elevation = 2.dp
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Drain: ${if (drainStatus.value) "opened" else "closed"}",
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            fontWeight = FontWeight.SemiBold,
                        )

                        Text(
                            text = "Crane: ${if (craneStatus.value) "opened" else "closed"}",
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }

                    Spacer(modifier = Modifier.width(45.dp))

                    CustomButton(
                        backgroundColor = Color.White,
                        contentColor = Color.DarkGray,
                        icon = if (drainStatus.value)
                            painterResource(id = R.drawable.ic_close_drain)
                        else
                            painterResource(id = R.drawable.ic_open_drain),
                        contentDescription = "",
                        contentPaddingValues = PaddingValues(10.dp),
                        borderStroke = BorderStroke(2.dp, Color.DarkGray),
                        onClick = { viewModel.onChangeDrainStatus() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .height(100.dp)
                    .background(color = Color.White),
                shape = RoundedCornerShape(10.dp),
                elevation = 2.dp
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Slider(
                        modifier = Modifier.requiredWidth(200.dp),
                        value = 1f,
                        onValueChange = {},
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Blue,
                            activeTrackColor = Color.Blue
                        )
                    )

                    Spacer(modifier = Modifier.width(45.dp))

                    Text(
                        text = "${temperature.value}℃",
                        fontSize = MaterialTheme.typography.h5.fontSize,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .height(100.dp)
                    .background(color = Color.White),
                shape = RoundedCornerShape(10.dp),
                elevation = 2.dp
            ) {
                ButtonGroup(
                    modifier = Modifier.padding(start = 35.dp, end = 35.dp),
                    buttons = listOf(WaterColor.Red, WaterColor.Normal, WaterColor.Blue),
                    onChangeColor = { color -> viewModel.onChangeWaterColor(color) },
                    selectedButton = selectedColor,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    contentPaddingValues = PaddingValues(30.dp),
                    borderStrokeColor = Color.Black,
                    borderStrokeWidth = 2.dp
                )
            }
        }
    } else {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.size(75.dp),
                strokeWidth = 5.dp
            )
        }
        viewModel.onUpdateState()
    }
}

