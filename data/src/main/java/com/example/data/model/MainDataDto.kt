package com.example.data.model

import com.example.data.core.DataMapper
import com.example.domain.model.MainDataModel

data class MainDataDto(
    val temp: Double,
    val humidity: Int,
): DataMapper<MainDataModel> {

    override fun toDomain() = MainDataModel(
        temp = temp,
        humidity = humidity
    )
}