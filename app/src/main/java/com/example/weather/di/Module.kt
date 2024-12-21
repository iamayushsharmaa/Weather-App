package com.example.weather.di

import com.example.weather.domain.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object Module {


    @Provides
    fun provideWeatherRepository(retrofit: Retrofit): WeatherRepository {
        return Retrofit.Builder()
            .baseUrl()
    }

}