package com.example.jetweatherforecast.repository

import android.util.Log
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.model.WeatherObject
import com.example.jetweatherforecast.networks.WeatherApi
import retrofit2.http.Query
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api :WeatherApi) {
    suspend fun getWather(cityQuery: String): DataOrException<Weather,Boolean,Exception>{
        val response = try{
        api.getWeather(query = cityQuery)
        }
        catch (e: Exception){
            Log.d("rexi","getweather$e")
            return DataOrException(e = e)
        }
        Log.d("ınd","getweather$response")
        return DataOrException(data = response)
    }
}