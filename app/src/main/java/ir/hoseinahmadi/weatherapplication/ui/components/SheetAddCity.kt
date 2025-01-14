package ir.hoseinahmadi.weatherapplication.ui.components

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ir.hoseinahmadi.weatherapplication.data.db.WeatherItem
import ir.hoseinahmadi.weatherapplication.util.CollectResult
import ir.hoseinahmadi.weatherapplication.util.getColorForWeatherOrTemperature
import ir.hoseinahmadi.weatherapplication.viewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SheetAddCity(
    mainViewModel: MainViewModel,
    allWeathers: List<WeatherItem>,
    onCityClick: (String) -> Unit,
    onAdd: (String) -> Unit,
) {
    val context = LocalContext.current
    val show by mainViewModel.showSheetAddCity.collectAsState()
    if (!show) return
    val sheetState = rememberModalBottomSheetState(true)
    var loading by remember { mutableStateOf(false) }

    CollectResult(
        flow = mainViewModel.weatherState,
        onError = {
            loading = false
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        },
        onLoading = {
            loading = true
        },
        onSuccess = {
            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
            onAdd(it.cityName)
            mainViewModel.updateSheetAddCityState(false)
            loading = false
        }
    )
    ModalBottomSheet(
        shape = RoundedCornerShape(12.dp),
        containerColor = Color.Black,
        sheetState = sheetState,
        onDismissRequest = { mainViewModel.updateSheetAddCityState(false) },
        modifier = Modifier.statusBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            stickyHeader {
                AddSection(
                    loading = loading,
                    enableSearch = allWeathers.size < 5
                ) { mainViewModel.fetchWeather(cityName = it) }
            }
            items(items = allWeathers, key = { it.name }) {
                SingleWeatherItem(
                    weatherItem = it,
                    onDeleted = { mainViewModel.deletedWeatherItem(it) },
                    onClick = {
                        mainViewModel.updateSheetAddCityState(false)
                        onCityClick(it.name)
                    },

                )
            }
        }


    }

}

@Composable
private fun SingleWeatherItem(
   weatherItem: WeatherItem,
    onDeleted: () -> Unit = {},
    onClick: () -> Unit = {},
) {
    val colors: Pair<Color, Color> = remember( key1 = weatherItem) {
        getColorForWeatherOrTemperature(
            weather = weatherItem.weather.getOrNull(0)?.main,
            temp = weatherItem.main.temp,
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp, horizontal = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(colors.first)
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = weatherItem.name,
            style = MaterialTheme.typography.bodyLarge,
            color = colors.second,
            fontWeight = FontWeight.SemiBold
        )
        IconButton(
            onClick = onDeleted
        ) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "",
                tint =colors.second
            )
        }
    }
}

@Composable
private fun AddSection(
    loading: Boolean,
    enableSearch: Boolean,
    onAdd: (String) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (enableSearch) {
            focusRequester.requestFocus()
        }
    }
    OutlinedTextField(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 8.dp)
            .focusRequester(focusRequester),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.LightGray,
            focusedLabelColor = Color.White,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            unfocusedLabelColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color.White,
            focusedPlaceholderColor = Color.White,
        ),
        label = {
            Text(
                text = "city",
            )
        },
        placeholder = {
            Text(
                text = "Enter Name City",
            )
        },
        readOnly = !enableSearch,
        value = searchQuery,
        onValueChange = { searchQuery = it },
        shape = RoundedCornerShape(12.dp),
        trailingIcon = {
            if (loading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(22.dp))
            } else {
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White,
                        disabledContentColor = Color.LightGray
                    ),
                    enabled = enableSearch && searchQuery.isNotEmpty(),
                    onClick = { onAdd(searchQuery) }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AddCircle,
                        contentDescription = "",
                    )
                }
            }

        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onAdd(searchQuery)
            },
        ),
        supportingText = {
            if (!enableSearch) {
                Text(
                    text = "Restrictions on adding a city",
                    color = Color.Red,
                )
            }
        }
    )
}