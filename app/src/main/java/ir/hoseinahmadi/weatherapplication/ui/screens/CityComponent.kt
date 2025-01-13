package ir.hoseinahmadi.weatherapplication.ui.screens

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hoseinahmadi.weatherapplication.R
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
                text = "${weatherItem.main.temp.roundToInt()} °C",
                color = Color.White,
                fontSize = 50.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = greetingMessage,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            HorizontalDivider(
                modifier = Modifier
                    .width(60.dp)
                    .padding(vertical = 35.dp),
                thickness = 1.dp,
                color = Color.LightGray.copy(0.8f)
            )

            Row(
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxWidth(0.84f),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom
            ) {
                BottomComponent(
                    icon = R.drawable.windy,
                    title = "WIND",
                    value = windSpeed
                )
                VerticalDivider(
                    modifier = Modifier
                        .height(50.dp)
                        .padding(horizontal = 12.dp),
                    thickness = 1.dp,
                    color = Color.White
                )
                BottomComponent(
                    icon = R.drawable.summer,
                    title = "SUNSET",
                    value = sunsetTime
                )
                VerticalDivider(
                    modifier = Modifier
                        .height(50.dp)
                        .padding(horizontal = 12.dp),
                    thickness = 1.dp,
                    color = Color.White
                )
                BottomComponent(
                    icon = R.drawable.humidity,
                    title = "HUMIDITY",
                    value ="${weatherItem.main.humidity}%"
                )

            }
        }


    }
}

@Composable
fun BottomComponent(
    @DrawableRes icon: Int,
    title: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.size(34.dp)
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = Color.White
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = value,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color = Color.White
        )
    }
}