package com.example.weatherapp_android.api

import com.example.weatherapp_android.models.weatherdatamodel.WeatherData
import com.example.weatherapp_android.models.weatherforecastdatamodel.WeatherForecastData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherDataService {

    @GET("/data/2.5/weather")
    suspend fun getWeatherData(@Query("lat") latitude: Double,
                               @Query("lon") longitude: Double,
                               @Query("units") units: String,
                               @Query("appid") apiKey: String): Response<WeatherData>

    @GET("/data/2.5/forecast")
    suspend fun getWeatherForecastData(@Query("lat") latitude: Double,
                               @Query("lon") longitude: Double,
                               @Query("units") units: String,
                               @Query("appid") apiKey: String): Response<WeatherForecastData>
}