package ir.hoseinahmadi.weatherapplication.ui.components

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import ir.hoseinahmadi.weatherapplication.util.CollectResult
import ir.hoseinahmadi.weatherapplication.viewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetAddCity(mainViewModel: MainViewModel) {

    val show by mainViewModel.showSheetAddCity.collectAsState()
    val sheetState = rememberModalBottomSheetState(true)
    val context = LocalContext.current
    CollectResult(
        flow = mainViewModel.weatherState,
        onError = {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        },
        onLoading = {},
        onSuccess = {
            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
            mainViewModel.updateSheetAddCityState(false)
        }
    )
    if (!show) return
    var txt by remember { mutableStateOf("") }
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { mainViewModel.updateSheetAddCityState(false) }
    ) {
        TextField(
            value = txt,
            onValueChange = { txt = it }
        )
        Button(onClick = {
            mainViewModel.fetchWeather(cityName = txt)
        }) {
            Text("addd")
        }
    }

}