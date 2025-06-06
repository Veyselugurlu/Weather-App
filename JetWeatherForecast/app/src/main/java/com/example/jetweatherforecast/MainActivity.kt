package com.example.jetweatherforecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetweatherforecast.navigation.WeatherNavigation
import com.example.jetweatherforecast.ui.theme.JetWeatherForecastTheme
import dagger.hilt.android.AndroidEntryPoint

//https://api.openweathermap.org/data/2.5/forecast/daily?q=lisbon&appid=ed60fcfbd110ee65c7150605ea8aceea&units=imperial
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        WeatherApp()
    }
}

@Composable
fun WeatherApp(){
    JetWeatherForecastTheme {
        Surface( modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background) {
           Column(verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally) {
               WeatherNavigation()
           }
        }
    }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetWeatherForecastTheme {
    }
}