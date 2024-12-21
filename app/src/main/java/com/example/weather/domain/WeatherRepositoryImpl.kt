package com.example.weather.domain

import com.example.weather.data.model.Weather
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

}