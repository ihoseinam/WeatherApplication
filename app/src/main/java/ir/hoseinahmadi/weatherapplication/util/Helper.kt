package ir.hoseinahmadi.weatherapplication.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import ir.hoseinahmadi.weatherapplication.data.db.WeatherItem
import ir.hoseinahmadi.weatherapplication.data.model.apiCall.NetWorResult
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

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
)

fun getColorForTemperature(temp: Double): Color {
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
        "02d", "02n" -> R.drawable.few_clouds // کمی ابری
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
