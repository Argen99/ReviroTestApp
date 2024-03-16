package com.example.data.remote.api_service

import com.example.data.model.CityDto
import com.example.data.model.WeatherResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApiService {

    @GET("data/2.5/forecast")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("appid") id: String = KEY
    ): Response<WeatherResponseDto>

    @GET("geo/1.0/direct")
    suspend fun searchCities(
        @Query("q") city: String,
        @Query("limit") limit: Int = Int.MAX_VALUE,
        @Query("appid") id: String = KEY
    ) : Response<List<CityDto>>

    companion object {
        const val KEY = "5b49f8456f02d6d98613edc89de608ec"
    }
}