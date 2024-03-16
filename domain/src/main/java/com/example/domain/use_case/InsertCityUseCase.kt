package com.example.domain.use_case

import com.example.domain.model.CityModel
import com.example.domain.repository.MainRepository

class InsertCityUseCase(
    private val repository: MainRepository
) {
    suspend operator fun invoke(city: CityModel) = repository.insertCity(city)
}