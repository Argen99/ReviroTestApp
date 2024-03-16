package com.example.data.remote.repository

import app.cash.turbine.test
import com.example.data.model.CityDto
import com.example.data.model.WeatherResponseDto
import com.example.data.remote.api_service.MainApiService
import com.example.data.room.CitiesDao
import com.example.domain.core.Either
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import retrofit2.Response

class MainRepositoryImplTest {

    val mockCitiesDao = mock<CitiesDao>()
    val mockApiService = mock<MainApiService>()

    @After
    fun after() {
        Mockito.reset(mockCitiesDao, mockApiService)
    }

    @Test
    fun `getWeather emits success when API calls are successful`() = runTest {
        val mockCities = listOf(CityDto("Bishkek","KG"))
        val mockWeatherResponse = WeatherResponseDto("",0,0, emptyList(), CityDto("",""))
        val mockApiResponse = Response.success(mockWeatherResponse)

        Mockito.`when`(mockCitiesDao.getCities()).thenReturn(flowOf(mockCities))
        Mockito.`when`(mockApiService.getWeather("Bishkek")).thenReturn(mockApiResponse)

        val sut = MainRepositoryImpl(mockApiService, mockCitiesDao)

        sut.getWeather().test {
            val result = awaitItem()
            assertThat(result is Either.Success).isTrue()
            awaitComplete()
        }
    }

    @Test
    fun `getWeather emits error when API calls fail`() = runTest {
        val mockCities = listOf(CityDto("Bishkek","KG"))
        val mockErrorResponse = Response.error<WeatherResponseDto>(401, "Unauthorized".toResponseBody())

        Mockito.`when`(mockCitiesDao.getCities()).thenReturn(flowOf(mockCities))
        Mockito.`when`(mockApiService.getWeather("Bishkek")).thenReturn(mockErrorResponse)

        val sut = MainRepositoryImpl(mockApiService, mockCitiesDao)

        sut.getWeather().test {
            val result = awaitItem()
            assertThat(result is Either.Error).isTrue()
            awaitComplete()
        }
    }

    @Test
    fun `searchCities emits success when API calls are successful`() = runTest {
        Mockito.`when`(mockApiService.searchCities(""))
            .thenReturn(Response.success(emptyList()))
        val sut = MainRepositoryImpl(mockApiService, mockCitiesDao)
        sut.searchCities("").test {
            val item = awaitItem()
            assertThat(item is Either.Success).isTrue()
            awaitComplete()
        }
    }

    @Test
    fun `searchCities emits error when API calls fail`() = runTest {
        Mockito.`when`(mockApiService.searchCities(""))
            .thenReturn(Response.error(401,"Unauthorized".toResponseBody()))
        val sut = MainRepositoryImpl(mockApiService, mockCitiesDao)
        sut.searchCities("").test {
            val item = awaitItem()
            assertThat(item is Either.Error).isTrue()
            awaitComplete()
        }
    }

    @Test
    fun `insertCity returns true when city is successfully inserted`() = runTest {
        val mockCity = CityDto("","")
        Mockito.`when`(mockCitiesDao.insertCity(mockCity)).thenReturn(1)
        val sut = MainRepositoryImpl(mockApiService, mockCitiesDao)
        val result = sut.insertCity(mockCity.toDomain())
        assertThat(result).isTrue()
    }

    @Test
    fun `insertCity returns false when city is successfully inserted`() = runTest {
        val mockCity = CityDto("","")
        Mockito.`when`(mockCitiesDao.insertCity(mockCity)).thenReturn(0)
        val sut = MainRepositoryImpl(mockApiService, mockCitiesDao)
        val result = sut.insertCity(mockCity.toDomain())
        assertThat(result).isFalse()
    }

    @Test
    fun `deleteCity returns true when city is successfully deleted`() = runTest {
        val mockCity = CityDto("","")
        Mockito.`when`(mockCitiesDao.deleteCity(mockCity)).thenReturn(1)
        val sut = MainRepositoryImpl(mockApiService, mockCitiesDao)
        val result = sut.deleteCity(mockCity.toDomain())
        assertThat(result).isTrue()
    }

    @Test
    fun `deleteCity returns false when city is successfully deleted`() = runTest {
        val mockCity = CityDto("","")
        Mockito.`when`(mockCitiesDao.deleteCity(mockCity)).thenReturn(0)
        val sut = MainRepositoryImpl(mockApiService, mockCitiesDao)
        val result = sut.deleteCity(mockCity.toDomain())
        assertThat(result).isFalse()
    }
}