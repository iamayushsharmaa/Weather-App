@file:Suppress("UNREACHABLE_CODE")

package com.example.weather.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather.data.model.UiState
import com.example.weather.data.remote.respond.Weather
import com.example.weather.presentation.component.DayBigCard
import com.example.weather.presentation.component.NightSmallCard
import com.example.weather.ui.theme.poppinsFontFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var city by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val temp = uiState
    LaunchedEffect (city){
        viewModel.fetchCityWeather("Bhopal")
    }
    Scaffold (
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Weather",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color.Black,
                        modifier = Modifier.background(Color.White)
                    )
                },
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
        ) {
            when (uiState) {
                is UiState.Loading -> {
                    Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                }
                is UiState.Success -> {
                    Log.d("gettemp1", "get success")
                    val weather = (uiState as UiState.Success<Weather>).data

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        item {
                            DayBigCard(
                                isExpanded = true,
                                weather = weather,
                                city = city
                            )
                        }
                        item {
                            NightSmallCard(
                                isExpanded = true,
                                weather = weather,
                                city = city
                            )
                        }
                        item {

                        }
                    }

                }
                is UiState.Error -> {
                    val errorMessage = (uiState as UiState.Error).message
                    Log.d("gettemp", errorMessage)
                    ErrorView(errorMessage) {
                        viewModel.fetchCityWeather("CityName")
                   }
                }
            }

        }
    }
}

@Composable
fun ErrorView(errorMessage: String, content: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "error"
            )
            Text(
                text = errorMessage,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 20.dp) // Adjust the padding as needed
            )
        }
    }
}

