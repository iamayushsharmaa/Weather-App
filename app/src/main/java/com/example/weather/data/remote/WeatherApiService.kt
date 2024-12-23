package com.example.weather.data.remote

import com.example.weather.data.remote.respond.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("/data/2.5/weather")
    suspend fun getCityWeather(
        @Query("q") city : String,
        @Query("appid") apikey : String = API_KEY,
        @Query("units") units: String = "metric"
    ) : Weather

    companion object{
        val BASE_URL = "https://api.openweathermap.org"
        val API_KEY = "4ecb5a67cb608b867bd177e90bb968e9"
    }
}