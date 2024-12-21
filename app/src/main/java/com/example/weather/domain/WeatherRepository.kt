package com.example.weather.domain

import com.example.weather.data.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getCityWeather(city : String) : Flow<Resource<Weather>>

}