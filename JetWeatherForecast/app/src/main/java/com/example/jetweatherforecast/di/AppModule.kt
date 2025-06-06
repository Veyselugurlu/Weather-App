package com.example.jetweatherforecast.di

import com.example.jetweatherforecast.model.WeatherObject
import com.example.jetweatherforecast.networks.WeatherApi
import com.example.jetweatherforecast.repository.WeatherRepository
import com.example.jetweatherforecast.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun getprovideOpemWeatherApi(): WeatherApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(WeatherApi::class.java)
    }

}