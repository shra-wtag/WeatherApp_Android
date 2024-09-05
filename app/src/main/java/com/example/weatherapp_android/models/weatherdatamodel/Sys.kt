package com.example.weatherapp_android.models.weatherdatamodel

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)