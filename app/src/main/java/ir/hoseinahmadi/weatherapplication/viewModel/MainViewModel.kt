package ir.hoseinahmadi.weatherapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.hoseinahmadi.weatherapplication.data.db.WeatherItem
import ir.hoseinahmadi.weatherapplication.data.model.apiCall.NetWorResult
import ir.hoseinahmadi.weatherapplication.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository,
) : ViewModel() {
    init {
        getAllWeather()
    }

    private val _allWeather = MutableStateFlow<List<WeatherItem>>(emptyList())
    val allWeather: StateFlow<List<WeatherItem>> = _allWeather.asStateFlow()
    private fun getAllWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllWeather().collectLatest {
                _allWeather.emit(it)
            }
        }
    }

    private val _weatherState =
        MutableSharedFlow<NetWorResult>()
    val weatherState: SharedFlow<NetWorResult> = _weatherState.asSharedFlow()

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

    private val _showSheetAddCity = MutableStateFlow(false)
    val showSheetAddCity: StateFlow<Boolean> = _showSheetAddCity.asStateFlow()
    fun updateSheetAddCityState(visibility: Boolean) {
        _showSheetAddCity.value = visibility
    }

    private val _showSheetMore = MutableStateFlow(false)
    val showSheetMore: StateFlow<Boolean> = _showSheetMore.asStateFlow()
     fun deletedWeatherItem(weather: WeatherItem){
         viewModelScope.launch(Dispatchers.IO) { repository.deletedWeatherItem(weather) }
     }

}