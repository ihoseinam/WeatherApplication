package ir.hoseinahmadi.weatherapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ir.hoseinahmadi.weatherapplication.ui.screens.MainScreen
import ir.hoseinahmadi.weatherapplication.ui.theme.WeatherApplicationTheme
import ir.hoseinahmadi.weatherapplication.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherApplicationTheme {
                MainScreen(mainViewModel)
            }
        }
    }
}

