package com.example.data.remote.repository

import com.example.data.model.toDto
import com.example.data.remote.api_service.MainApiService
import com.example.data.room.CitiesDao
import com.example.domain.core.Either
import com.example.domain.model.CityModel
import com.example.domain.model.WeatherResponseModel
import com.example.domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainRepositoryImpl(
    private val apiService: MainApiService,
    private val citiesDao: CitiesDao
) : MainRepository {

    override fun getWeather() = flow<Either<List<WeatherResponseModel>>> {
        citiesDao.getCities().collect { list ->
            val weatherData = mutableListOf<WeatherResponseModel>()
            list.forEach { city ->
                val result = apiService.getWeather(city.name)
                if (result.isSuccessful && result.body() != null) {
                    weatherData.add(result.body()!!.toDomain())
                } else {
                    emit(Either.Error(Exception("Unknown error")))
                    return@collect
                }
            }
            emit(Either.Success(weatherData))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(Either.Error(Exception(it.localizedMessage ?: "Unknown error")))
    }

    override fun searchCities(city: String): Flow<Either<List<CityModel>>> = flow {
        val result = apiService.searchCities(city)
        if (result.isSuccessful && result.body() != null) {
            emit(Either.Success(result.body()!!.map { it.toDomain() }))
        } else {
            emit(Either.Error(Exception("Unknown error")))
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(Either.Error(Exception(it.localizedMessage ?: "Unknown error")))
    }

    override suspend fun insertCity(city: CityModel): Boolean {
        return citiesDao.insertCity(city.toDto()) >= 1
    }

    override suspend fun deleteCity(city: CityModel): Boolean {
        return citiesDao.deleteCity(city.toDto()) > 0
    }
}