package ir.hoseinahmadi.weatherapplication

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import ir.hoseinahmadi.weatherapplication.data.model.WeatherResponse
import ir.hoseinahmadi.weatherapplication.data.model.apiCall.NetWorResult
import ir.hoseinahmadi.weatherapplication.util.Constants

class MainRepository(
    private val client: HttpClient
) {
    suspend fun getWeatherByCoordinates(
        latitude: Double? = null,
        longitude: Double? = null,
        city: String? = null,
        apiKey: String = Constants.WEATHER_API_KEY,
        units: String = "metric"
    ): NetWorResult<WeatherResponse> {
        return try {
            val response: HttpResponse =
                client.get("https://api.openweathermap.org/data/2.5/weather") {
                    url {
                        parameters.append("appid", apiKey)
                        parameters.append("units", units)
                        parameters.append("lat", latitude.toString())
                        parameters.append("lon", longitude.toString())
                        parameters.append("q", city.toString())
                    }
                }

            if (response.status.value in 200..299) {
                val responseBody = response.body<WeatherResponse>()
                NetWorResult.Success(responseBody)
            } else {
                NetWorResult.Error("Error: ${response.status.value} - ${response.status.description}")
            }
        } catch (e: Exception) {
            NetWorResult.Error("Exception: ${e.message}")
        }
    }

}