package ir.hoseinahmadi.weatherapplication.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.hoseinahmadi.weatherapplication.data.model.Clouds
import ir.hoseinahmadi.weatherapplication.data.model.Coord
import ir.hoseinahmadi.weatherapplication.data.model.Main
import ir.hoseinahmadi.weatherapplication.data.model.Sys
import ir.hoseinahmadi.weatherapplication.data.model.Weather
import ir.hoseinahmadi.weatherapplication.data.model.Wind

@Entity
data class WeatherItem(
    val id: Int,
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
    @PrimaryKey
    val name: String,
    val cod: Int,
    val lastUpdate:String,
)