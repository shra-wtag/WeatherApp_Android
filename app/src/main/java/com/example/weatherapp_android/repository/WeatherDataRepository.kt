package com.example.weatherapp_android.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp_android.api.WeatherDataService
import com.example.weatherapp_android.models.weatherdatamodel.WeatherData
import com.example.weatherapp_android.models.weatherforecastdatamodel.WeatherForecastData
import retrofit2.Response

class WeatherDataRepository(private val weatherDataService: WeatherDataService) {
    suspend fun getWeatherData(latitude: Double, longitude: Double, units: String, apiKey: String): Response<WeatherData> {
        val result = weatherDataService.getWeatherData(latitude, longitude, units,  apiKey)
        return result
    }

    suspend fun getWeatherForecastData(latitude: Double, longitude: Double, units: String, apiKey: String): Response<WeatherForecastData> {
        val result = weatherDataService.getWeatherForecastData(latitude, longitude, units,  apiKey)
        return result
    }

    fun getWeatherIcon(icon: String): String {
        return "https://openweathermap.org/img/wn/${icon}@2x.png"
    }
}