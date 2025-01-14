package ir.hoseinahmadi.weatherapplication.repository

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import ir.hoseinahmadi.weatherapplication.data.db.WeatherItem
import ir.hoseinahmadi.weatherapplication.data.db.dao.WeatherDao
import ir.hoseinahmadi.weatherapplication.data.model.WeatherResponse
import ir.hoseinahmadi.weatherapplication.data.model.apiCall.NetWorResult
import ir.hoseinahmadi.weatherapplication.util.Constants
import kotlinx.coroutines.flow.Flow

class MainRepository(
    private val client: HttpClient,
    private val dao: WeatherDao
) {

    fun getAllWeather(): Flow<List<WeatherItem>> = dao.getAllWeather()
    private suspend fun getWeatherByCityName(cityName: String): WeatherItem? {
        return dao.getWeatherByCityName(cityName)
    }

    suspend fun getWeatherByCoordinates(
        latitude: Double? = null,
        longitude: Double? = null,
        city: String? = null,
        apiKey: String = Constants.WEATHER_API_KEY,
        units: String = "metric"
    ): NetWorResult {
        return try {
            val response: HttpResponse =
                client.get("https://api.openweathermap.org/data/2.5/weather") {
                    url {
                        parameters.append("appid", apiKey)
                        parameters.append("units", units)
                        latitude?.let { parameters.append("lat", it.toString()) }
                        longitude?.let { parameters.append("lon", it.toString()) }
                        city?.let { parameters.append("q", it) }
                    }
                }

            if (response.status.value in 200..299) {
                val responseBody = response.body<WeatherResponse>()
                dao.upsertWeather(responseBody.calculateToWeatherItem(System.currentTimeMillis().toString()))
                NetWorResult.Success(responseBody.name)
            } else {
                val cachedData = city?.let { getWeatherByCityName(it) }
                cachedData?.let {
                    NetWorResult.Success(it.name)
                } ?: NetWorResult.Error(response.status.description)
            }
        } catch (e: Exception) {
            val cachedData = city?.let { getWeatherByCityName(it) }
            cachedData?.let {
                NetWorResult.Success(it.name)
            } ?: NetWorResult.Error("Error")
        }
    }

    suspend fun deletedWeatherItem(weather: WeatherItem){dao.deletedWeatherItem(weather)}

}