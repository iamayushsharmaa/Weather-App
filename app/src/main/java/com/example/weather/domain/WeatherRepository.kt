package com.example.weather.domain

import com.example.weather.data.model.Resource
import com.example.weather.data.remote.respond.Weather
import com.example.weather.data.remote.respond.WeatherX
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface WeatherRepository {
    suspend fun getCityWeather(city : String) : Flow<Resource<Weather>>
    suspend fun getNightCityWeather(city: String): Flow<Resource<Weather>>

}