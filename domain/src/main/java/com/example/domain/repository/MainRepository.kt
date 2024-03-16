package com.example.domain.repository

import com.example.domain.core.Either
import com.example.domain.model.CityModel
import com.example.domain.model.WeatherResponseModel
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getWeather(): Flow<Either<List<WeatherResponseModel>>>
    fun searchCities(city: String): Flow<Either<List<CityModel>>>
    suspend fun insertCity(city: CityModel): Boolean
    suspend fun deleteCity(city: CityModel): Boolean
}