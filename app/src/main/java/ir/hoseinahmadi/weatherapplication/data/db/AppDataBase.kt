package ir.hoseinahmadi.weatherapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.hoseinahmadi.weatherapplication.data.db.dao.WeatherDao

@Database(entities =[WeatherItem::class], exportSchema = false, version = 1 )
@TypeConverters(WeatherTypeConverter::class)
abstract class AppDataBase:RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

}