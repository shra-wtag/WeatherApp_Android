package com.example.weatherapp_android

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp_android.api.RetrofitHelper
import com.example.weatherapp_android.api.WeatherDataService
import com.example.weatherapp_android.databinding.FragmentWeatherInfoBinding
import com.example.weatherapp_android.repository.WeatherDataRepository
import com.example.weatherapp_android.viewmodels.WeatherInfoViewModel
import com.example.weatherapp_android.viewmodels.WeatherInfoViewModelFactory

class WeatherInfoFragment : Fragment() {
    private lateinit var binding: FragmentWeatherInfoBinding
    private lateinit var weatherInfoViewModel: WeatherInfoViewModel
    private lateinit var weatherForecastAdapter: WeatherForecastAdapter
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dailyForecastRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        val weatherDataService = RetrofitHelper.getRetrofitInstance().create(WeatherDataService::class.java)

        val repository = WeatherDataRepository(weatherDataService)

        weatherInfoViewModel = ViewModelProvider(this, WeatherInfoViewModelFactory(repository)).get(WeatherInfoViewModel::class.java)

        weatherInfoViewModel.getWeatherData()

        updateWeatherData()
        updateWeatherIcon()
        updateForecastData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateForecastData() {
        weatherInfoViewModel.getWeatherForecastData()
        weatherForecastAdapter = WeatherForecastAdapter(requireContext(), emptyList())
        binding.dailyForecastRecyclerView.adapter = weatherForecastAdapter

        weatherInfoViewModel.forecastData.observe(viewLifecycleOwner) {
            weatherForecastAdapter.updateDailyForecastData(it)
        }
    }

    private fun updateWeatherIcon() {
        weatherInfoViewModel.icon.observe(viewLifecycleOwner) { icon ->
            icon?.let {
                Glide.with(this)
                    .load(it)
                    .into(binding.imgIcon)
            }
        }
    }

    private fun updateWeatherData() {
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