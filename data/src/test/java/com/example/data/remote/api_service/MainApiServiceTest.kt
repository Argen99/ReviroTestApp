package com.example.data.remote.api_service

import com.example.data.model.CityDto
import com.example.data.model.WeatherResponseDto
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainApiServiceTest {

    private lateinit var server: MockWebServer
    private lateinit var api: MainApiService

    @Before
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MainApiService::class.java)
    }

    @After
    fun after() {
        server.shutdown()
    }

    @Test
    fun `getWeather, returns success`() = runBlocking {
        val dto = WeatherResponseDto(
            cod = "", message = 0, cnt = 0, list = emptyList(),
            city = CityDto(name = "", country = "")
        )
        val gson: Gson = GsonBuilder().create()
        val json = gson.toJson(dto)!!
        val mockResponse = MockResponse()
        mockResponse.setBody(json)
        server.enqueue(mockResponse)

        val response = api.getWeather("").body()
        server.takeRequest()

        assertThat(response).isEqualTo(dto)
    }

    @Test
    fun `getWeather, returns error`() = runBlocking {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)
        mockResponse.setBody("error")
        server.enqueue(mockResponse)

        val response = api.getWeather("")
        server.takeRequest()

        assertThat(response.isSuccessful).isFalse()
        assertThat(response.code()).isEqualTo(404)
    }
}