package com.example.data.model

import com.example.data.core.DataMapper
import com.example.domain.model.WeatherDataModel
import com.google.gson.annotations.SerializedName

data class WeatherDataDto(
    val main: MainDataDto,
    val weather: List<WeatherDto>,
    val wind: WindDto,
    @SerializedName("dt_txt")
    val date: String,
): DataMapper<WeatherDataModel> {
    override fun toDomain() = WeatherDataModel(
        main = main.toDomain(),
        weather = weather.map { it.toDomain() },
        wind = wind.toDomain(),
        date = date,
    )
}