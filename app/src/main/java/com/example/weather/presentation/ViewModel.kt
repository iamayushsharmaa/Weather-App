package com.example.weather.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.model.Weather
import com.example.weather.domain.Resource
import com.example.weather.domain.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository : WeatherRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow<UiState<Weather>>(UiState.Loading)
    val uiState: StateFlow<UiState<Weather>> = _uiState.asStateFlow()

    fun fetchCityWeather(city: String) {
        viewModelScope.launch {
            repository.getCityWeather(city).collect{ result ->
                when(result){
                    is Resource.Loading -> {
                        _uiState.value = UiState.Loading
                    }
                    is Resource.Success -> {
                        Log.d("data", "fetch weather for thhis city ")
                        _uiState.value = UiState.Success(result.data)
                    }
                    is Resource.Error -> {
                        _uiState.value = UiState.Error(result.exception.message ?: "Unknown error")
                    }
                }

            }
        }
    }


}