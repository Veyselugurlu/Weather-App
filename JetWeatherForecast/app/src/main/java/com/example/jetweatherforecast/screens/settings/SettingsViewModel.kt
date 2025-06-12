package com.example.jetweatherforecast.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweatherforecast.model.Unit
import com.example.jetweatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: WeatherDbRepository): ViewModel(){

    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged()
                .collect{ listOfUnits ->
                    if (listOfUnits.isNullOrEmpty()) {
                        Log.d("TAG","Empty Units")
                    }
                    else {
                        _unitList.value = listOfUnits

                    }
                }
        }
    }

    fun insertUnit(unit: Unit) = viewModelScope.launch { repository.insertUnits(unit) }
    fun updateUnit(unit: Unit) = viewModelScope.launch { repository.updateUnits(unit) }
    fun deleteUnit(unit: Unit) = viewModelScope.launch { repository.deleteUnits(unit) }
    fun deleteAllUnit() = viewModelScope.launch { repository.deleteAllFavorite() }

}