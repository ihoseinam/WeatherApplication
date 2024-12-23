package ir.hoseinahmadi.weatherapplication

import android.app.Application
import ir.hoseinahmadi.weatherapplication.di.NetworkModule
import ir.hoseinahmadi.weatherapplication.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App:Application()  {
    override fun onCreate() {
        super.onCreate()
            startKoin{
                androidContext(this@App)
                modules(NetworkModule, viewModelModule)
            }
    }
}