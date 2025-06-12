package com.example.jetweatherforecast.repository

import android.util.Log
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.networks.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api : WeatherApi) {

    suspend fun getWeather(cityQuery: String, units: String)
    : DataOrException<Weather,Boolean,Exception>{
        val response = try{
            api.getWeather(query = cityQuery,units = units)
        }
        catch (e: Exception){
            Log.d("rexi","getweather$e")
            return DataOrException(e = e)
        }
        Log.d("ınd","getweather$response")
        return DataOrException(data = response)
    }
}