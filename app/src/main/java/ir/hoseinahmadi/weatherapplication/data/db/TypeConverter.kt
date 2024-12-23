package ir.hoseinahmadi.weatherapplication.data.db

import androidx.room.TypeConverter
import ir.hoseinahmadi.weatherapplication.data.model.Clouds
import ir.hoseinahmadi.weatherapplication.data.model.Coord
import ir.hoseinahmadi.weatherapplication.data.model.Main
import ir.hoseinahmadi.weatherapplication.data.model.Sys
import ir.hoseinahmadi.weatherapplication.data.model.Weather
import ir.hoseinahmadi.weatherapplication.data.model.Wind
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class WeatherTypeConverter {

    @TypeConverter
    fun fromWeatherList(weatherList: List<Weather>): String {
        return Json.encodeToString(weatherList)
    }

    @TypeConverter
    fun toWeatherList(weatherListString: String): List<Weather> {
        return Json.decodeFromString(weatherListString)
    }

    @TypeConverter
    fun fromCoord(coord: Coord): String {
        return Json.encodeToString(coord)
    }

    @TypeConverter
    fun toCoord(coordString: String): Coord {
        return Json.decodeFromString(coordString)
    }

    @TypeConverter
    fun fromMain(main: Main): String {
        return Json.encodeToString(main)
    }

    @TypeConverter
    fun toMain(mainString: String): Main {
        return Json.decodeFromString(mainString)
    }

    @TypeConverter
    fun fromWind(wind: Wind): String {
        return Json.encodeToString(wind)
    }

    @TypeConverter
    fun toWind(windString: String): Wind {
        return Json.decodeFromString(windString)
    }

    @TypeConverter
    fun fromClouds(clouds: Clouds): String {
        return Json.encodeToString(clouds)
    }

    @TypeConverter
    fun toClouds(cloudsString: String): Clouds {
        return Json.decodeFromString(cloudsString)
    }

    @TypeConverter
    fun fromSys(sys: Sys): String {
        return Json.encodeToString(sys)
    }

    @TypeConverter
    fun toSys(sysString: String): Sys {
        return Json.decodeFromString(sysString)
    }
}


