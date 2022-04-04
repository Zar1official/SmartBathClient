package ru.zar1official.smartbathclient.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.zar1official.smartbathclient.MainScreen
import ru.zar1official.smartbathclient.domain.repository.Repository
import ru.zar1official.smartbathclient.ui.theme.SmartBathClientTheme

class MainActivity : ComponentActivity() {
    private val repository: Repository by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                MainScreen()
            }
        }
        lifecycleScope.launch {
            Toast.makeText(
                applicationContext,
                repository.readBathState().craneActie.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SmartBathClientTheme {
        Greeting("Android")
    }
}