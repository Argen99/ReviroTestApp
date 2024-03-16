package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.core.DataMapper
import com.example.domain.model.CityModel

@Entity(tableName = "cities")
data class CityDto(
    @PrimaryKey
    val name: String,
    val country: String
): DataMapper<CityModel> {
    override fun toDomain() = CityModel(
        name = name,
        country = country
    )
}

fun CityModel.toDto() = CityDto(
    name = name,
    country = country
)