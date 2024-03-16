package com.example.domain.model

data class WeatherDataModel(
    val main: MainDataModel,
    val weather: List<WeatherModel>,
    val wind: WindModel,
    val date: String,
)