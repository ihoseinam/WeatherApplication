package ir.hoseinahmadi.weatherapplication.data.model

import ir.hoseinahmadi.weatherapplication.data.db.WeatherItem
import kotlinx.serialization.Serializable
@Serializable
data class WeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
){
    fun calculateToWeatherItem(time: String): WeatherItem {
        return WeatherItem(
            coord = this.coord,
            weather = this.weather,
            base = this.base,
            main = this.main,
            visibility = this.visibility,
            wind = this.wind,
            clouds = this.clouds,
            dt = this.dt,
            sys = this.sys,
            timezone = this.timezone,
            id = this.id,
            name = this.name,
            cod = this.cod,
            lastUpdate = time
        )
    }
}
@Serializable
data class Coord(
    val lon: Double,
    val lat: Double
)
@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
@Serializable
data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val sea_level: Int? = null,
    val grnd_level: Int? = null
)
@Serializable
data class Wind(
    val speed: Double,
    val deg: Int
)
@Serializable
data class Clouds(
    val all: Int
)
@Serializable
data class Sys(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
