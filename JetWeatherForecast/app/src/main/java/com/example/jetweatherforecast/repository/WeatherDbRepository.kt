package com.example.jetweatherforecast.repository

import com.example.jetweatherforecast.data.WeatherDao
import com.example.jetweatherforecast.model.Favorite
import com.example.jetweatherforecast.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao){

    fun getFavorites() : Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun deleteAllFavorite() = weatherDao.deleteAllFavorites()
    suspend fun getfavById(city: String): Favorite = weatherDao.getFavById(city)


    //settings table
    fun getUnits() : Flow<List<Unit>> = weatherDao.getUnits()
    suspend fun insertUnits(unit: Unit) = weatherDao.insertUnit(unit)
    suspend fun updateUnits(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteUnits(unit: Unit) = weatherDao.deleteUnit(unit)
    suspend fun deleteAllUnits(unit: Unit) = weatherDao.deleteAllUnit()

}