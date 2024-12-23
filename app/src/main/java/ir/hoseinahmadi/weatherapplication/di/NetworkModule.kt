package ir.hoseinahmadi.weatherapplication.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import ir.hoseinahmadi.weatherapplication.MainRepository
import ir.hoseinahmadi.weatherapplication.viewModel.MainViewModel
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel<MainViewModel> { MainViewModel(get()) }
}
val NetworkModule = module {
    single {
        HttpClient(Android) {
            install(ContentNegotiation) { json(Json { this.ignoreUnknownKeys = true }) }
        }
    }
    single { MainRepository(get(),get()) }
}
