package ir.hoseinahmadi.weatherapplication.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ir.hoseinahmadi.weatherapplication.ui.components.MainBackground
import ir.hoseinahmadi.weatherapplication.ui.components.MainTopBar
import ir.hoseinahmadi.weatherapplication.ui.components.SheetAddCity
import ir.hoseinahmadi.weatherapplication.viewModel.MainViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val allWeathers by mainViewModel.allWeather.collectAsState()
    SheetAddCity(mainViewModel = mainViewModel,allWeathers)
    var ttt by remember { mutableStateOf("") }
    var temp by remember { mutableDoubleStateOf(25.0) }
    val pagerState = rememberPagerState { allWeathers.size }
    MainBackground(
        temp = allWeathers.getOrNull(pagerState.currentPage)?.main?.temp ?: 0.0,
        weather = allWeathers.getOrNull(pagerState.currentPage)?.weather?.getOrNull(0)?.main
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = { MainTopBar(mainViewModel) }
        ) { innerPadding ->
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) { page ->
                CityComponent(allWeathers[page], mainViewModel = mainViewModel)
            }
        }
    }


}