package com.example.domain.di

import com.example.domain.use_case.DeleteCityUseCase
import com.example.domain.use_case.GetWeatherUseCase
import com.example.domain.use_case.InsertCityUseCase
import com.example.domain.use_case.SearchCityUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetWeatherUseCase)
    factoryOf(::SearchCityUseCase)
    factoryOf(::InsertCityUseCase)
    factoryOf(::DeleteCityUseCase)
}