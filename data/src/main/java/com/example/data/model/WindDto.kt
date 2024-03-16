package com.example.data.model

import com.example.data.core.DataMapper
import com.example.domain.model.WindModel

data class WindDto(
    val speed: Double
): DataMapper<WindModel> {
    override fun toDomain() = WindModel(
        speed = speed
    )
}