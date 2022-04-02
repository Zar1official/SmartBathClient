package ru.zar1official.smartbathclient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.zar1official.smartbathclient.components.CustomButton
import ru.zar1official.smartbathclient.components.Timer
import ru.zar1official.smartbathclient.ui.theme.DarkGreen

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val percentage = viewModel.percentage.observeAsState(0)
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
                    Timer(
                        inactiveBarColor = Color.Gray,
                        activeBarColor = Color.Blue,
                        modifier = Modifier.size(200.dp),
                        strokeWidth = 10.dp,
                        viewModel = viewModel
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
                        onClick = {}
                    )

                    Spacer(modifier = Modifier.width(45.dp))

                    CustomButton(
                        backgroundColor = Color.Red,
                        icon = painterResource(id = R.drawable.ic_turn_off),
                        contentDescription = "",
                        onClick = {}
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

        }
    }
}

