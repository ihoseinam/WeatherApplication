package ir.hoseinahmadi.weatherapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.hoseinahmadi.weatherapplication.MainRepository
import ir.hoseinahmadi.weatherapplication.data.model.WeatherResponse
import ir.hoseinahmadi.weatherapplication.data.model.apiCall.NetWorResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private val _weatherState =
        MutableSharedFlow<NetWorResult<WeatherResponse>>()
    val weatherState: SharedFlow<NetWorResult<WeatherResponse>> = _weatherState.asSharedFlow()

    fun fetchWeather(
        lat: Double? = null,
        long: Double? = null,
        cityName: String? = null
    ) {
        viewModelScope.launch {
            _weatherState.emit(NetWorResult.Loading)
            _weatherState.emit(repository.getWeatherByCoordinates(lat, long, city = cityName))
        }
    }
}