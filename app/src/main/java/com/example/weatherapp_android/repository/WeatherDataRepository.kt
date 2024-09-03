package com.example.weatherapp_android.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp_android.api.WeatherDataService
import com.example.weatherapp_android.models.WeatherData

class WeatherDataRepository(private val weatherDataService: WeatherDataService) {
    private val weatherLiveData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData>
        get() = weatherLiveData

    suspend fun getWeatherData(latitude: Double, longitude: Double, units: String, apiKey: String) {
        val result = weatherDataService.getWeatherData(latitude, longitude, units,  apiKey)
        if(result?.body() != null) {
            weatherLiveData.postValue(result.body())
        }
    }
}