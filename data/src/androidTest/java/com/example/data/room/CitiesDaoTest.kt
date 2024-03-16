package com.example.data.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import app.cash.turbine.test
import com.example.data.model.CityDto
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@SmallTest
class CitiesDaoTest {

    private lateinit var citiesDao: CitiesDao
    private lateinit var db: AppDataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        citiesDao = db.getCitiesDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertCityItem() = runTest {
        val cityItem = CityDto("Bishkek", "KG")
        citiesDao.insertCity(cityItem)

        citiesDao.getCities().test {
            val cities = awaitItem()
            assertThat(cities).contains(cityItem)
            cancel()
        }
    }

    @Test
    fun deleteCityItem() = runTest {
        val cityItem = CityDto("Bishkek", "KG")
        citiesDao.insertCity(cityItem)
        citiesDao.deleteCity(cityItem)

        citiesDao.getCities().test {
            val cities = awaitItem()
            assertThat(cities).doesNotContain(cityItem)
            cancel()
        }
    }
}