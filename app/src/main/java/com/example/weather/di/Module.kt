package com.example.weather.di


import com.example.weather.data.remote.WeatherApiService
import com.example.weather.data.remote.WeatherApiService.Companion.BASE_URL
import com.example.weather.domain.WeatherRepository
import com.example.weather.domain.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherApiService: WeatherApiService): WeatherRepository {
        return WeatherRepositoryImpl(weatherApiService)
    }

}