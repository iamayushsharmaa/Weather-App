package com.example.weather.domain

import com.example.weather.data.model.Resource
import com.example.weather.data.remote.respond.Weather
import com.example.weather.data.remote.WeatherApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApiService : WeatherApiService
) : WeatherRepository {
    override suspend fun getCityWeather(city: String): Flow<Resource<Weather>> = flow {
        emit(Resource.Loading)
        try {
            val weatherData = weatherApiService.getCityWeather(city)
            if (weatherData != null){
                emit(Resource.Success(weatherData))
            }else{
                emit(Resource.Error(Exception("No data found")))
            }
        }catch (e : Exception){
            emit(Resource.Error(e))
        }
    }

    override suspend fun getNightCityWeather(city: String): Flow<Resource<Weather>> = flow {
        emit(Resource.Loading)
        try {
            // Fetch weather data (assuming it includes hourly forecast)
            val weatherData = weatherApiService.getCityWeather(city)
            if (weatherData != null) {
                // Filter weather data for night-time (6 PM to 6 AM)
                val nightWeather = filterNightWeather(weatherData)
                // Return the updated weather data with filtered night-specific information
                emit(Resource.Success(nightWeather))
            } else {
                emit(Resource.Error(Exception("No data found")))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    // Function to filter night-time weather (6 PM to 6 AM)
    private fun filterNightWeather(weather: Weather): Weather {
        // Filter the hourly forecast for night data (6 PM - 6 AM)
        val nightWeather = weather.copy(
            weather = weather.weather.filter { forecast ->
                val forecastTime = java.time.LocalDateTime.ofEpochSecond(forecast.id.toLong(), 0, java.time.ZoneOffset.UTC)
                forecastTime.hour >= 18 || forecastTime.hour < 6 // Check if it's night time (6 PM to 6 AM)
            }
        )
        return nightWeather
    }

}