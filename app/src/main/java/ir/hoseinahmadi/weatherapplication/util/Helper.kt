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
    Color(0xFF002B5B), // سرد (کمتر از 0 درجه) - آبی تیره
    Color(0xFF0D47A1), // خنک (0-10 درجه) - آبی روشن‌تر
    Color(0xFF4CAF50), // معتدل (10-20 درجه) - سبز ملایم
    Color(0xFFFFA726), // گرم (20-30 درجه) - نارنجی
    Color(0xFFD32F2F)  // داغ (بیشتر از 30 درجه) - قرمز
)

val weatherColors = mapOf(
    "Clear" to Color(0xFF2196F3), // صاف - آبی
    "Clouds" to Color(0xFF90A4AE), // ابری - خاکستری
    "Rain" to Color(0xFF546E7A), // بارانی - خاکستری تیره
    "Snow" to Color(0xFFB3E5FC), // برفی - آبی روشن
    "Thunderstorm" to Color(0xFF512DA8), // طوفانی - بنفش تیره
    "Haze" to Color(0xFF78909C), // مه‌آلود - خاکستری ملایم
    "Fog" to Color(0xFFB0BEC5), // مه - خاکستری
    "Drizzle" to Color(0xFF81D4FA), // نم‌باران - آبی روشن
    "Mist" to Color(0xFFECEFF1) // غبار - خاکستری بسیار روشن
)

fun getTextColorForBackground(backgroundColor: Color): Color {
    val luminance = (0.299 * backgroundColor.red + 0.587 * backgroundColor.green + 0.114 * backgroundColor.blue)
    return if (luminance > 0.5) Color.Black else Color.White
}

fun getColorForWeatherOrTemperature(weather: String?, temp: Double): Pair<Color, Color> {
    weather?.let {
        val weatherColor = weatherColors[it]
        if (weatherColor != null) {
            val textColor = getTextColorForBackground(weatherColor)
            return weatherColor to textColor
        }
    }
    val backgroundColor = when {
        temp < 0 -> temperatureColors[0]
        temp in 0.0..10.0 -> temperatureColors[1]
        temp in 10.0..20.0 -> temperatureColors[2]
        temp in 20.0..30.0 -> temperatureColors[3]
        else -> temperatureColors[4]
    }
    val textColor = getTextColorForBackground(backgroundColor)
    return backgroundColor to textColor
}


fun getWeatherIconResId(weatherCondition: String?): Int {
    return when (weatherCondition) {
        "Clear Day" -> R.drawable.clear_day // آسمان صاف (روز)
        "Clear Night" -> R.drawable.clear_night // آسمان صاف (شب)
        "Partly Cloudy Day" -> R.drawable.partly_cloudy_day // نیمه‌ابری (روز)
        "Partly Cloudy Night" -> R.drawable.cloudy_night_2 // نیمه‌ابری (شب)
        "Cloudy" -> R.drawable.cloudy_day_1 // کاملاً ابری
        "Fair Day" -> R.drawable.fair_day // صاف و دلپذیر (روز)
        "Fair Night" -> R.drawable.fair_night // صاف و دلپذیر (شب)
        "Fog" -> R.drawable.fog // مه
        "Rain" -> R.drawable.rain // بارانی
        "Rain and Snow Mix" -> R.drawable.rain_and_snow_mix // ترکیب باران و برف
        "Scattered Thunderstorms" -> R.drawable.scattered_thunderstorms // رعد و برق پراکنده
        "Isolated Thunderstorms" -> R.drawable.isolated_thunderstorms // رعد و برق پراکنده
        "Snow" -> R.drawable.snowy_5 // برفی
        "Windy" -> R.drawable.windy // باد شدید
        "Summer" -> R.drawable.summer // تابستانی (گرم و آفتابی)
        "Haze" -> R.drawable.fog // مه‌آلود
        "Mist" -> R.drawable.fog // غبار
        "Drizzle" -> R.drawable.rain_and_sleet_mix // نم‌باران (بارش خفیف)
        else -> R.drawable.day // حالت پیش‌فرض (ابری)
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
