package ir.hoseinahmadi.weatherapplication.ui.screens

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
fun CityComponent(
    weatherItem: WeatherItem,
    mainViewModel: MainViewModel,
    textColor: Color,
) {

    val context = LocalContext.current
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
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        },
        onLoading = {},
        onSuccess = {
            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()

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
                color = textColor,
                fontSize = 35.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = formattedDate,
                color = textColor,
                fontSize = 20.sp,
            )
            val icon =
                remember(key1 = weatherItem) { getWeatherIconResId(weatherItem.weather.getOrNull(0)?.main) }
            Image(
                painter = painterResource(icon),
                contentDescription = "",
                Modifier.size(300.dp),
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
                color = textColor,
                fontSize = 50.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = greetingMessage,
                color = textColor,
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
                    value = windSpeed,
                    color = textColor
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
                    value = sunsetTime,
                    color = textColor

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
                    value = "${weatherItem.main.humidity}%",
                    color = textColor
                )

            }
        }


    }
}

@Composable
fun BottomComponent(
    @DrawableRes icon: Int,
    title: String,
    value: String,
    color: Color,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            tint = color,
            modifier = Modifier.size(34.dp)
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = color
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = value,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            color =color
        )
    }
}