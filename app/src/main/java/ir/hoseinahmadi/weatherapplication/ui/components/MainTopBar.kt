package ir.hoseinahmadi.weatherapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ir.hoseinahmadi.weatherapplication.viewModel.MainViewModel

@Composable
fun MainTopBar(mainViewModel: MainViewModel) {
    Row(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(9.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = "",
                tint = Color.White
            )
        }
        IconButton(onClick = { mainViewModel.updateSheetAddCityState(true) }) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "",
                tint = Color.White
            )

        }
    }
}