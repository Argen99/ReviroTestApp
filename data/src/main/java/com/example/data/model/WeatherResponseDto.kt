package com.example.data.model

import com.example.data.core.DataMapper
import com.example.domain.model.WeatherResponseModel

data class WeatherResponseDto(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<WeatherDataDto>,
    val city: CityDto
): DataMapper<WeatherResponseModel> {

    override fun toDomain() = WeatherResponseModel(
        cod = cod,
        message = message,
        cnt = cnt,
        list = list.map { it.toDomain() },
        city = city.toDomain(),
    )
}
