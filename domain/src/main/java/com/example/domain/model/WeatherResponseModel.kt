package com.example.domain.model

data class WeatherResponseModel(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<WeatherDataModel>,
    val city: CityModel
)