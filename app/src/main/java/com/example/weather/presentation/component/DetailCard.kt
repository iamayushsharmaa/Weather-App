package com.example.weather.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather.data.remote.respond.Weather
import com.example.weather.presentation.WeatherViewModel

@Composable
fun DetailCard(
    weather: Weather,
    viewModel: WeatherViewModel = hiltViewModel()
) {

    val weatherDetails = remember { mutableListOf<Pair<String, String>>() }

    weatherDetails.add("Temperature" to "${weather.main.temp}°C")
    weatherDetails.add("Humidity" to "${weather.main.humidity}%")
    weatherDetails.add("Pressure" to "${weather.main.pressure} hPa")
    weatherDetails.add("Wind Speed" to "${weather.wind.speed} m/s")
    weatherDetails.add("Visibility" to "${weather.visibility} m")
    weatherDetails.add("Cloudiness" to "${weather.clouds.all}%")



    val sunriseTime = viewModel.formatUnixTimestamp(weather.sys.sunrise.toLong())
    val sunsetTime = viewModel.formatUnixTimestamp(weather.sys.sunset.toLong())

    // Add sunrise and sunset to the list
    weatherDetails.add("Sunrise" to sunriseTime)
    weatherDetails.add("Sunset" to sunsetTime)


    Card(
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(38.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray,
            contentColor = Color.White
        )
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
        ){
            LazyVerticalGrid(
                columns = GridCells.Fixed(6), // 6 columns in the grid
                modifier = Modifier.fillMaxSize()
            ) {
                items(weatherDetails) { (weatherDetailName, valueAndUnit) ->
                    val value = valueAndUnit.split(" ")[0]
                    val unit = valueAndUnit.split(" ").getOrElse(1) { "" }

                    WeatherAdditionalInfo(
                        weatherDetailName = weatherDetailName,
                        weatherDetailValue = value,
                        weatherDetailValueUnit = unit
                    )
                }
            }
        }
    }
}