package ir.hoseinahmadi.weatherapplication.di

import androidx.room.Room
import ir.hoseinahmadi.weatherapplication.data.db.AppDataBase
import org.koin.dsl.module

val AppDataBaseModule = module {
    single<AppDataBase> {
        Room.databaseBuilder(
            context = get(),
            klass = AppDataBase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration()
            .build()
    }
}
val weatherDaoModule = module {
    single { get<AppDataBase>().weatherDao() }
}
