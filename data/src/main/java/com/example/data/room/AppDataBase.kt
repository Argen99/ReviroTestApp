package com.example.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.CityDto

@Database(version = 1, entities = [CityDto::class])
abstract class AppDataBase: RoomDatabase() {

    abstract fun getCitiesDao(): CitiesDao
}