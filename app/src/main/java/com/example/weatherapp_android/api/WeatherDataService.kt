package com.example.weatherapp_android.api

import com.example.weatherapp_android.models.WeatherData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherDataService {

    @GET("/data/2.5/weather")
    suspend fun getWeatherData(@Query("lat") latitude: Double,
                               @Query("lon") longitude: Double,
                               @Query("units") units: String,
                               @Query("appid") apiKey: String): Response<WeatherData>
    @GET("/img/wn/{icon}@2x.png")
    suspend fun getWeatherIcon(@Path("icon") icon: String): ResponseBody

}