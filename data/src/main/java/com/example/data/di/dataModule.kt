package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.remote.api_service.MainApiService
import com.example.data.remote.repository.MainRepositoryImpl
import com.example.data.room.AppDataBase
import com.example.data.room.CitiesDao
import com.example.domain.repository.MainRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    singleOf(::MainRepositoryImpl) {
        bind<MainRepository>()
    }
    factoryOf(::provideOkHttpClient)
    factoryOf(::provideForecastApi)
    factoryOf(::provideRetrofit)
    singleOf(::provideRoomDB)
    singleOf(::provideCitiesDao)
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient().newBuilder()
        .addInterceptor(interceptor)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()
}

fun provideForecastApi(retrofit: Retrofit): MainApiService {
    return retrofit.create(MainApiService::class.java)
}

fun provideRoomDB(context: Context) : AppDataBase {
    return Room.databaseBuilder(context, AppDataBase::class.java, "database.db").build()
}

fun provideCitiesDao(roomDB: AppDataBase): CitiesDao {
    return roomDB.getCitiesDao()
}