package ir.hoseinahmadi.weatherapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ir.hoseinahmadi.weatherapplication.util.getColorForWeatherOrTemperature

@Composable
fun MainBackground(
    color: Color,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
        )
        content()
    }
}