package com.example.weatherapp_android.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp_android.repository.WeatherDataRepository
import com.example.weatherapp_android.models.weatherdatamodel.WeatherData
import com.example.weatherapp_android.models.weatherforecastdatamodel.WeatherForecastData
import com.example.weatherapp_android.models.weatherforecastdatamodel._List
import com.example.weatherapp_android.utilities.CurrentDateTime

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WeatherInfoViewModel(private val repository: WeatherDataRepository): ViewModel() {
    private val apiKey = "f7fba2a96004431c6e3b90fb0728bd89"

    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> get() = _weatherData

    private val _icon = MutableLiveData<String>()
    val icon: LiveData<String> get() = _icon

    private val _forecastData = MutableLiveData<MutableList<_List>>()
    val forecastData: LiveData<MutableList<_List>> get() = _forecastData

    private val _forecastIcon = MutableLiveData<String>()
    val forecastIcon: LiveData<String> get() = _icon

    fun getWeatherData() {
        viewModelScope.launch {
            val response = repository.getWeatherData(
                23.8103,
                90.4125,
                "metric",
                apiKey
            )

            if(response.isSuccessful && response.body() != null) {
                _weatherData.postValue(response.body())
                getWeatherIcon("${response.body()?.weather?.first()?.icon}")
            } else if(response.errorBody() != null) {
                //TODO
            }

        }
    }

    private fun getWeatherIcon(icon: String) {
        val iconUrl = repository.getWeatherIcon(icon)
        _icon.postValue(iconUrl)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeatherForecastData() {
        viewModelScope.launch {
            val forecastData = repository.getWeatherForecastData(
                23.8103,
                90.4125,
                "metric",
                apiKey
            )

            if (forecastData.isSuccessful && forecastData.body() != null) {
                val dailyForecastTemperatures: MutableList<_List> = arrayListOf()
                val forecastIconUrls: MutableList<String> = arrayListOf()
                forecastData.body()?.let {
                    for (i in 0..<8) {
                        dailyForecastTemperatures.add(it.list[i])

                    }
                }
                _forecastData.postValue(dailyForecastTemperatures)
            }
        }
    }

    private fun getWeatherForecastIcon(icon: String) {
        val iconUrl = repository.getWeatherIcon(icon)
        _forecastIcon.postValue(iconUrl)
    }
}