package com.example.weatherapp_android

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp_android.api.RetrofitHelper
import com.example.weatherapp_android.api.WeatherDataService
import com.example.weatherapp_android.databinding.FragmentWeatherInfoBinding
import com.example.weatherapp_android.repository.WeatherDataRepository
import com.example.weatherapp_android.viewmodels.WeatherInfoViewModel
import com.example.weatherapp_android.viewmodels.WeatherInfoViewModelFactory

class WeatherInfoFragment : Fragment() {
    private lateinit var binding: FragmentWeatherInfoBinding
    lateinit var weatherInfoViewModel: WeatherInfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weatherDataService = RetrofitHelper.getRetrofitInstance().create(WeatherDataService::class.java)

        val repository = WeatherDataRepository(weatherDataService)

        weatherInfoViewModel = ViewModelProvider(this, WeatherInfoViewModelFactory(repository)).get(WeatherInfoViewModel::class.java)

        weatherInfoViewModel.weatherData.observe(viewLifecycleOwner) {
            binding.tvLocation.text = it.name
            binding.tvTemperature.text = "${(it.main.temp).toInt()} C"
            binding.tvDescription.text = it.weather.first().description
            binding.tvPressure.text = "${it.main.pressure}"
            binding.tvhumidity.text = "${it.main.humidity}"
            binding.tvVisibility.text = "${it.visibility}"
            binding.tvWindSpeed.text = "${it.wind.speed}"

        }
    }
}