package ir.hoseinahmadi.weatherapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ir.hoseinahmadi.weatherapplication.data.db.WeatherItem
import ir.hoseinahmadi.weatherapplication.data.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("select * from weatheritem")
    fun getAllWeather(): Flow<List<WeatherItem>>

    @Upsert
    suspend fun upsertWeather(weather: WeatherItem)

    @Query("select * from weatheritem where name=:cityName")
   suspend fun getWeatherByCityName(cityName: String): WeatherItem?

}