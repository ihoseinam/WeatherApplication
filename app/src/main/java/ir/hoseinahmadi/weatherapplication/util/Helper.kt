package ir.hoseinahmadi.weatherapplication.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import ir.hoseinahmadi.weatherapplication.R
import ir.hoseinahmadi.weatherapplication.data.db.WeatherItem
import ir.hoseinahmadi.weatherapplication.data.model.apiCall.NetWorResult
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val temperatureColors = listOf(
    Color(0xFF001F3F), // سرد (کمتر از 0 درجه)
    Color(0xFF0074D9), // خنک (0-10 درجه)
    Color(0xFF7FDBFF), // معتدل (10-20 درجه)
    Color(0xFF2ECC40), // گرم (20-30 درجه)
    Color(0xFFFFDC00), // داغ (بیشتر از 30 درجه)
)

val weatherColors = mapOf(
    "Clear" to Color(0xFF87CEEB), // صاف
    "Clouds" to Color(0xFFB0C4DE), // ابری
    "Rain" to Color(0xFF708090), // بارانی
    "Snow" to Color(0xFFFFFFFF), // برفی
    "Thunderstorm" to Color(0xFF4B0082), // طوفانی
    "Haze" to Color(0xFF778899), // مه‌آلود
    "Fog" to Color(0xFFB0AFAF), // مه
    "Drizzle" to Color(0xFFA4D3EE), // نم‌باران
    "Mist" to Color(0xFFD3D3D3) // غبار
)

fun getColorForWeatherOrTemperature(weather: String?, temp: Double): Color {
/*    weather?.let {
        val weatherColor = weatherColors[it]
        if (weatherColor != null) {
            return weatherColor
        }
    }*/
    return when {
        temp < 0 -> temperatureColors[0]
        temp in 0.0..10.0 -> temperatureColors[1]
        temp in 10.0..20.0 -> temperatureColors[2]
        temp in 20.0..30.0 -> temperatureColors[3]
        else -> temperatureColors[4]
    }
}

/*
fun getBackgroundResource(icon: String): Int {
    return when (icon) {
        "01d", "01n" -> R.drawable.clear_sky // آسمان صاف
        "02d", "02n" -> R.drawable.few_clouds // کمی ابری0.
        "03d", "03n" -> R.drawable.scattered_clouds // پراکنده ابری
        "04d", "04n" -> R.drawable.broken_clouds // ابری متراکم
        "09d", "09n" -> R.drawable.shower_rain // بارش پراکنده
        "10d", "10n" -> R.drawable.rain // بارانی
        "11d", "11n" -> R.drawable.thunderstorm // طوفانی
        "13d", "13n" -> R.drawable.snow // برفی
        "50d", "50n" -> R.drawable.mist // مه
        else -> R.drawable.default_background // پیش‌فرض
    }
}
*/

fun getWeatherIconResId(weatherCondition: String?): Int {
    return when (weatherCondition) {
        "Clear" -> R.drawable.icon3 // آسمان صاف
        "Clouds" -> R.drawable.icon14 // ابری
        "Rain" -> R.drawable.icon7 // بارانی
        "Snow" -> R.drawable.icon1 // برفی
        "Thunderstorm" -> R.drawable.icon8 // طوفانی
        "Haze" -> R.drawable.icon6 // مه‌آلود
        "Fog" -> R.drawable.icon10 // مه
        "Drizzle" -> R.drawable.icon5 // نم‌باران
        "Mist" -> R.drawable.icon11 // غبار
        "Windy" -> R.drawable.icon13 // باد شدید
        else -> R.drawable.icon14 // حالت پیش‌فرض (ابری)
    }
}

@Composable
fun CollectResult(
    flow: SharedFlow<NetWorResult>,
    onSuccess: (NetWorResult.Success) -> Unit,
    onError: (message: String) -> Unit,
    onLoading: () -> Unit,
    apiCall :(() -> Unit )?=null
) {
    LaunchedEffect(Unit) {
        apiCall?.invoke()
        flow.collectLatest { result ->
            when (result) {
                is NetWorResult.Error -> onError(result.resMessage?:"Error")
                NetWorResult.Loading -> onLoading()
                NetWorResult.NotCall -> {}
                is NetWorResult.Success -> onSuccess(result)
            }
        }
    }
}





fun formatSunTime(timestamp: Long, timezone: Int): String {
    val date = Date((timestamp + timezone) * 1000)
    val dateFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
    return dateFormat.format(date)
}

fun getGreetingMessage(weatherItem: WeatherItem): String {
    val currentTime = System.currentTimeMillis() / 1000
    return if (currentTime in weatherItem.sys.sunrise..weatherItem.sys.sunset) {
        "Good Day\n${weatherItem.name}"
    } else {
        "Good Night\n${weatherItem.name}"
    }
}
fun isUpdateAvailable(lastUpdateMillis: Long?): Boolean {
    if (lastUpdateMillis == null) { return false }
    val currentTimeMillis = System.currentTimeMillis()
    val twoHoursInMillis = 2 * 60 * 60 * 1000
    return (currentTimeMillis - lastUpdateMillis) > twoHoursInMillis
}


