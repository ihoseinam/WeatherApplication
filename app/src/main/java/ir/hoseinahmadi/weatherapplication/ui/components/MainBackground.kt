package ir.hoseinahmadi.weatherapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ir.hoseinahmadi.weatherapplication.util.getColorForTemperature

@Composable
fun MainBackground(
    temp: Double,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(getColorForTemperature(temp))
        )
        content()
    }
}