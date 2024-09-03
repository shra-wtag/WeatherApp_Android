package com.example.weatherapp_android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp_android.repository.WeatherDataRepository


class WeatherInfoViewModelFactory(private val repository: WeatherDataRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherInfoViewModel(repository) as T
    }
}