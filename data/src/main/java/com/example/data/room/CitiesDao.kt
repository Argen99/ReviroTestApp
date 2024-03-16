package com.example.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.CityDto
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao {

    @Query("SELECT * FROM cities")
    fun getCities(): Flow<List<CityDto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCity(city: CityDto): Long

    @Delete
    suspend fun deleteCity(city: CityDto): Int
}