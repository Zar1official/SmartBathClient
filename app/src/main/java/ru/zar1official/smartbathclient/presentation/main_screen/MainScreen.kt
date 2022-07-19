package ru.zar1official.smartbathclient

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel
import ru.zar1official.smartbathclient.domain.usecases.WaterColor
import ru.zar1official.smartbathclient.presentation.components.AppBarWithTitle
import ru.zar1official.smartbathclient.presentation.components.ButtonGroup
import ru.zar1official.smartbathclient.presentation.components.CustomButton
import ru.zar1official.smartbathclient.presentation.components.CustomProgress
import ru.zar1official.smartbathclient.presentation.main_screen.MainScreenEvent
import ru.zar1official.smartbathclient.presentation.main_screen.MainScreenIntent
import ru.zar1official.smartbathclient.presentation.main_screen.MainViewModel
import ru.zar1official.smartbathclient.ui.theme.DarkGreen
import ru.zar1official.smartbathclient.util.round

@Composable
fun MainScreen(viewModel: MainViewModel = getViewModel()) {
    val scaffoldState = rememberScaffoldState()
    val snackBarErrorMessage = stringResource(id = R.string.error_message)
    val snackBarErrorLabel = stringResource(id = R.string.error_label)
    val screenState = viewModel.screenState

    Scaffold(
        topBar = {
            AppBarWithTitle(
                title = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Left
            )
        }, scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LaunchedEffect(true) {
                viewModel.event.collectLatest { event ->
                    when (event) {
                        is MainScreenEvent.Error -> {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = snackBarErrorMessage,
                                duration = SnackbarDuration.Short,
                            )
                        }
                        is MainScreenEvent.LoadingError -> {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = snackBarErrorMessage,
                                duration = SnackbarDuration.Indefinite,
                                actionLabel = snackBarErrorLabel
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.onSendIntent(MainScreenIntent.UpdateState)
                            }
                        }
                    }
                }
            }
            if (screenState.isLoaded) {
                LazyColumn(
                    verticalArrangement = Arrangement.Center,
                    contentPadding = PaddingValues(
                        top = 5.dp,
                        bottom = 5.dp,
                        start = 10.dp,
                        end = 10.dp
                    ),
                    content = {
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
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
                                            percentage = screenState.percentage
                                        )
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = "${screenState.percentage}%",
                                                fontSize = MaterialTheme.typography.h5.fontSize,
                                                fontWeight = FontWeight.SemiBold,
                                                textAlign = TextAlign.Center
                                            )

                                            Spacer(modifier = Modifier.height(5.dp))

                                            Text(
                                                text = "Water level",
                                                fontSize = MaterialTheme.typography.h6.fontSize,
                                                fontWeight = FontWeight.Normal
                                            )
                                        }

                                    }

                                    Row {
                                        CustomButton(
                                            backgroundColor = DarkGreen,
                                            icon = painterResource(id = R.drawable.ic_turn_off),
                                            contentDescription = "",
                                            contentPaddingValues = PaddingValues(20.dp),
                                            onClick = { viewModel.onSendIntent(MainScreenIntent.StartFetchWater) }
                                        )

                                        Spacer(modifier = Modifier.width(45.dp))

                                        CustomButton(
                                            backgroundColor = Color.Red,
                                            icon = painterResource(id = R.drawable.ic_turn_off),
                                            contentDescription = "",
                                            contentPaddingValues = PaddingValues(20.dp),
                                            onClick = { viewModel.onSendIntent(MainScreenIntent.StopFetchWater) }
                                        )
                                    }
                                }
                            }
                        }

                        item { Spacer(modifier = Modifier.height(25.dp)) }

                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
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
                                            text = "Drain: ${if (screenState.drainStatus) "opened" else "closed"}",
                                            fontSize = MaterialTheme.typography.h6.fontSize,
                                            fontWeight = FontWeight.SemiBold,
                                        )

                                        Text(
                                            text = "Crane: ${if (screenState.craneStatus) "opened" else "closed"}",
                                            fontSize = MaterialTheme.typography.h6.fontSize,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(45.dp))

                                    CustomButton(
                                        backgroundColor = Color.White,
                                        contentColor = Color.DarkGray,
                                        icon = if (screenState.drainStatus)
                                            painterResource(id = R.drawable.ic_close_drain)
                                        else
                                            painterResource(id = R.drawable.ic_open_drain),
                                        contentDescription = "",
                                        contentPaddingValues = PaddingValues(10.dp),
                                        borderStroke = BorderStroke(2.dp, Color.DarkGray),
                                        onClick = { viewModel.onSendIntent(MainScreenIntent.ChangeDrainStatus) }
                                    )
                                }
                            }
                        }

                        item { Spacer(modifier = Modifier.height(25.dp)) }

                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(75.dp)
                                    .background(color = Color.White),
                                shape = RoundedCornerShape(10.dp),
                                elevation = 2.dp
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Slider(
                                        modifier = Modifier.requiredWidth(200.dp),
                                        value = screenState.temperature,
                                        onValueChange = {
                                            viewModel.onSendIntent(
                                                MainScreenIntent.ChangeTemperature(
                                                    it
                                                )
                                            )
                                        },
                                        colors = SliderDefaults.colors(
                                            thumbColor = Color.Blue,
                                            activeTrackColor = Color.Blue
                                        ),
                                        onValueChangeFinished = {
                                            viewModel.onSendIntent(MainScreenIntent.SaveTemperature)
                                        },
                                        valueRange = 5f..65f
                                    )

                                    Spacer(modifier = Modifier.width(45.dp))

                                    Text(
                                        modifier = Modifier.requiredWidth(80.dp),
                                        text = "${screenState.temperature.round(decimals = 1)}℃",
                                        fontSize = MaterialTheme.typography.h5.fontSize,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                }
                            }
                        }

                        item { Spacer(modifier = Modifier.height(25.dp)) }

                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(125.dp)
                                    .background(color = Color.White),
                                shape = RoundedCornerShape(10.dp),
                                elevation = 2.dp
                            ) {
                                Text(
                                    text = "Water color",
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp,
                                )
                                ButtonGroup(
                                    modifier = Modifier.padding(
                                        top = 20.dp,
                                        start = 35.dp,
                                        end = 35.dp
                                    ),
                                    buttons = listOf(
                                        WaterColor.Red,
                                        WaterColor.Normal,
                                        WaterColor.Blue
                                    ),
                                    onChangeColor = { color ->
                                        viewModel.onSendIntent(
                                            MainScreenIntent.ChangeWaterColor(
                                                color
                                            )
                                        )
                                    },
                                    selectedButton = screenState.waterColor,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    contentPaddingValues = PaddingValues(30.dp),
                                    borderStrokeColor = Color.Black,
                                    borderStrokeWidth = 2.dp
                                )
                            }
                        }
                    })
            } else {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(75.dp),
                        strokeWidth = 5.dp
                    )
                }
                viewModel.onSendIntent(MainScreenIntent.UpdateState)
            }
        }
    }
}

