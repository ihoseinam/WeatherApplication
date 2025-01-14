package ir.hoseinahmadi.weatherapplication.ui.screens

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hoseinahmadi.weatherapplication.ui.components.MainBackground
import ir.hoseinahmadi.weatherapplication.ui.components.MainTopBar
import ir.hoseinahmadi.weatherapplication.ui.components.SheetAddCity
import ir.hoseinahmadi.weatherapplication.util.getCityIndex
import ir.hoseinahmadi.weatherapplication.util.getColorForWeatherOrTemperature
import ir.hoseinahmadi.weatherapplication.viewModel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val allWeathers by mainViewModel.allWeather.collectAsState()
    val pagerState = rememberPagerState { allWeathers.size }
    val coroutineScope = rememberCoroutineScope()
    val colors: Pair<Color, Color> = remember(key1 = pagerState.currentPage, key2 = allWeathers) {
        getColorForWeatherOrTemperature(
            weather = allWeathers.getOrNull(pagerState.currentPage)?.weather?.getOrNull(0)?.main,
            temp = allWeathers.getOrNull(pagerState.currentPage)?.main?.temp ?: 0.0,
        )
    }
    var enableUpdate by remember { mutableStateOf(true) }
    SheetAddCity(
        mainViewModel = mainViewModel,
        allWeathers = allWeathers,
        onAdd = { city ->
            coroutineScope.launch {
                delay(250)
                getCityIndex(cityList = allWeathers, cityName = city)?.let {
                    enableUpdate = false
                    pagerState.animateScrollToPage(it, animationSpec = tween(600))
                }
            }
        },
        onCityClick = { city ->
            getCityIndex(cityList = allWeathers, cityName = city)?.let {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(
                        it,
                        animationSpec = tween(600)
                    )
                }
            }
        }
    )
    MainBackground(
        color = colors.first
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = { MainTopBar(mainViewModel, color = colors.second) }
        ) { innerPadding ->
            if (allWeathers.isEmpty()) {
                EmptyCityList {
                    mainViewModel.updateSheetAddCityState(true)
                }
            } else {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) { page ->
                    val weatherItem =
                        remember(key1 = allWeathers, key2 = page) { allWeathers[page] }
                    CityComponent(
                        weatherItem = weatherItem,
                        mainViewModel = mainViewModel,
                        textColor = colors.second,
                        backColor = colors.first,
                        pageSize = allWeathers.size,
                        currentPage = page,
                        enableUpdate = enableUpdate,
                    )
                }

            }
        }
    }


}

@Composable
private fun EmptyCityList(
    onAdd: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "You have not added a city !",
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            modifier = Modifier.padding(6.dp)
        )
        IconButton(
            onClick = onAdd
        ) {
            Icon(
                imageVector = Icons.Rounded.AddCircle,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}