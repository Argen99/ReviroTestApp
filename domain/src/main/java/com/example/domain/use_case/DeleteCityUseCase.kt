package com.example.domain.use_case

import com.example.domain.model.CityModel
import com.example.domain.repository.MainRepository

class DeleteCityUseCase(
    private val repository: MainRepository
) {
    suspend operator fun invoke(city: CityModel) = repository.deleteCity(city)
}