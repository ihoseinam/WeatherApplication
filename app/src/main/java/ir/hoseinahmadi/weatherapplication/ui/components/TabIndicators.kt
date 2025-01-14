package ir.hoseinahmadi.weatherapplication.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun TabIndicators(
    pageSize: Int,
    currentPage: Int,
    backgroundColor: Color,
    textColor: Color,
) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageSize) { index ->
            Indicator(
                textColor = textColor,
                isSelected = index == currentPage
            )
        }
    }
}

@Composable
fun Indicator(
    isSelected: Boolean,
    textColor: Color,
) {
    val backColor by animateColorAsState(
        targetValue = if (isSelected) textColor else textColor.copy(0.4f),
        label = ""
    )
    val size by animateDpAsState(
        targetValue = if (isSelected) 7.dp else 5.dp,
        label = ""
    )
    Canvas(
        modifier = Modifier
            .padding(4.dp)
            .size(size),
    ) { drawCircle(color = backColor,) }


}