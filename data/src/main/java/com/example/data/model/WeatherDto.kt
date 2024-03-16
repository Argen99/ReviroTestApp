package com.example.data.model

import com.example.data.core.DataMapper
import com.example.domain.model.WeatherModel

data class WeatherDto(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
): DataMapper<WeatherModel> {
    override fun toDomain() = WeatherModel(
        id = id,
        main = main,
        description = description,
        icon = icon,
    )
}