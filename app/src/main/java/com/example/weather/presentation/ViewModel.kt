package com.example.weather.presentation

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.health.connect.datatypes.ExerciseRoute
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.model.UiState
import com.example.weather.data.remote.respond.Weather
import com.example.weather.data.model.Resource
import com.example.weather.domain.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val fusedLocationClient: FusedLocationProviderClient,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Weather>>(UiState.Loading)
    val uiState: StateFlow<UiState<Weather>> = _uiState.asStateFlow()

    fun fetchCityWeather(context: Context) {
        viewModelScope.launch {
            try {
                val location = getLocation()
                if (location != null) {
                    val city = getCityName(context, location.latitude, location.longitude)
                    if (!city.isNullOrEmpty()) {
                        repository.getCityWeather(city).collect { result ->
                            when (result) {
                                is Resource.Loading -> _uiState.value = UiState.Loading
                                is Resource.Success -> _uiState.value = UiState.Success(result.data)
                                is Resource.Error -> _uiState.value =
                                    UiState.Error(result.exception.message ?: "Unknown error")
                            }
                        }
                    }
                } else {
                    _uiState.value = UiState.Error("Unable to fetch location. Ensure it's enabled.")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private suspend fun getLocation(): Location? {
        return fusedLocationClient.await(context)
    }

    private fun getCityName(context: Context, latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        return addresses?.firstOrNull()?.locality
    }
}

suspend fun FusedLocationProviderClient.await(context: Context): Location? {
    val hasPermission = ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    if (!hasPermission) {
        // Log or handle the case where permissions are not granted
        return null
    }

    return suspendCancellableCoroutine { cont ->
        lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                cont.resume(task.result, null)
            } else {
                cont.resume(null, null)
            }
        }
    }
}
