package ir.hoseinahmadi.weatherapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hoseinahmadi.weatherapplication.data.db.WeatherItem
import ir.hoseinahmadi.weatherapplication.util.CollectResult
import ir.hoseinahmadi.weatherapplication.util.formatSunTime
import ir.hoseinahmadi.weatherapplication.util.getGreetingMessage
import ir.hoseinahmadi.weatherapplication.util.getWeatherIconResId
import ir.hoseinahmadi.weatherapplication.util.isUpdateAvailable
import ir.hoseinahmadi.weatherapplication.viewModel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun CityComponent(weatherItem: WeatherItem, mainViewModel: MainViewModel) {


    val temperature = "${weatherItem.main.temp.toInt()}°C"
    val greetingMessage = getGreetingMessage(weatherItem)
    val sunsetTime = formatSunTime(weatherItem.sys.sunset, weatherItem.timezone)
    val windSpeed = "${weatherItem.wind.speed}m/s"

    CollectResult(
        apiCall = {
            if (isUpdateAvailable(weatherItem.lastUpdate.toLongOrNull())) {
                mainViewModel.fetchWeather(cityName = weatherItem.name)
            }
        },
        flow = mainViewModel.weatherState,
        onError = {
            Log.i("2222", "onError :$it")
        },
        onLoading = {},
        onSuccess = {
            Log.i("2222", "update success")

        },
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val dateFormat = SimpleDateFormat("EEEE h:mm a", Locale.ENGLISH)
        val formattedDate =
            dateFormat.format(Date(weatherItem.lastUpdate.toLongOrNull() ?: 0)).uppercase()
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weatherItem.name,
                color = Color.White,
                fontSize = 35.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = formattedDate,
                color = Color.White,
                fontSize = 20.sp,
            )
            Spacer(Modifier.height(50.dp))
            val icon =
                remember(key1 = weatherItem) { getWeatherIconResId(weatherItem.weather.getOrNull(0)?.main) }
            Image(
                painter = painterResource(icon),
                contentDescription = "",
                Modifier.size(225.dp),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = greetingMessage,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = "${weatherItem.main.temp.roundToInt()} °C",
                color = Color.White,
                fontSize = 50.sp
            )

        }


    }
}
