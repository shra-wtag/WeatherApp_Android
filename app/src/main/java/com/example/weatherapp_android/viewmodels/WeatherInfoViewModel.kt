package com.example.weatherapp_android.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp_android.repository.WeatherDataRepository
import com.example.weatherapp_android.models.WeatherData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherInfoViewModel(private val repository: WeatherDataRepository): ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getWeatherData(23.8103, 90.4125, "metric", "f7fba2a96004431c6e3b90fb0728bd89")
        }
    }

    val weatherData: LiveData<WeatherData>
        get() = repository.weatherData


}