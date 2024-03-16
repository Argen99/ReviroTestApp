package com.example.revirotestapp.ui.fragments

import androidx.lifecycle.viewModelScope
import com.example.domain.model.CityModel
import com.example.domain.model.WeatherResponseModel
import com.example.domain.use_case.DeleteCityUseCase
import com.example.domain.use_case.GetWeatherUseCase
import com.example.revirotestapp.core.base.BaseViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val deleteCityUseCase: DeleteCityUseCase
): BaseViewModel() {

    private val _state = mutableUiStateFlow<List<WeatherResponseModel>>()
    val state = _state.asStateFlow()

    init {
        getWeather()
    }

    private fun getWeather() {
        getWeatherUseCase().gatherRequest(_state)
    }

    fun deleteCity(city: CityModel) {
        viewModelScope.launch {
            deleteCityUseCase(city)
        }
    }
}