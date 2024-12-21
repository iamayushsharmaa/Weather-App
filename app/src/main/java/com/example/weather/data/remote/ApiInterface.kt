package com.example.weather.data.remote

import com.example.weather.data.model.Weather
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApiService {

    @GET("/data/2.5/weather")
    suspend fun getCityWeather(
        @Path("city") city : String,
        @Path("apikey") apikey : String = API_KEY
    ) : Weather

    companion object{
        val URL = "https://api.openweathermap.org"
        val API_KEY = "4ecb5a67cb608b867bd177e90bb968e9"
    }
}