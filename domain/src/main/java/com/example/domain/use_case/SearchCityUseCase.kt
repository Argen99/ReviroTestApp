package com.example.domain.use_case

import com.example.domain.repository.MainRepository

class SearchCityUseCase(
    private val repository: MainRepository
) {
    operator fun invoke(city: String) = repository.searchCities(city)
}