package ir.hoseinahmadi.weatherapplication.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ir.hoseinahmadi.weatherapplication.R
import ir.hoseinahmadi.weatherapplication.viewModel.MainViewModel

@Composable
fun MainTopBar(
    mainViewModel: MainViewModel,
    color: Color,
) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(9.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            try {
                uriHandler.openUri("tg://resolve?domain=i_hoseinam")
            } catch (e: Exception) {
                Toast.makeText(context, "تلگرام یافت نشد", Toast.LENGTH_SHORT).show()
            }
        }) {
            Icon(
                painter = painterResource(R.drawable.support),
                contentDescription = "",
                tint = color,
                modifier = Modifier.size(20.dp)
            )
        }
        IconButton(onClick = { mainViewModel.updateSheetAddCityState(true) }) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "",
                tint = color,
                modifier = Modifier.size(25.dp)
            )

        }
    }
}