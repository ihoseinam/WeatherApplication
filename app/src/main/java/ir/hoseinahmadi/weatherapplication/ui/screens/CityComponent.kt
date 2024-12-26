package ir.hoseinahmadi.weatherapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hoseinahmadi.weatherapplication.data.db.WeatherItem
import ir.hoseinahmadi.weatherapplication.util.CollectResult
import ir.hoseinahmadi.weatherapplication.viewModel.MainViewModel
import kotlin.math.roundToInt

@Composable
fun CityComponent(weatherItem: WeatherItem, mainViewModel: MainViewModel) {

    /*    CollectResult(
            apiCall = { mainViewModel.fetchWeather(cityName = weatherItem.name) },
            flow = mainViewModel.weatherState,
            onError = {
                Log.i("2222", "onError :$it")
            },
            onLoading = {},
            onSuccess = {
                Log.i("2222", "onSuccess")

            },
        )*/
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = weatherItem.name,
            color = Color.White,
            fontSize = 35.sp
        )
        Spacer(Modifier.height(80.dp))
        Text(
            text ="${weatherItem.main.temp.roundToInt()}°C" ,
            color = Color.White,
            fontSize = 50.sp
        )


    }
}
