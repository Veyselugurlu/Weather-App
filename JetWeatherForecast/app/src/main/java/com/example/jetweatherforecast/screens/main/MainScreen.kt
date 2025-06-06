package com.example.jetweatherforecast.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.model.WeatherItem
import com.example.jetweatherforecast.screens.MainViewModel
import com.example.jetweatherforecast.widgets.WeatherAppBar
import formatDate
import formatDateTime
import formatDecimals

@Composable
fun MainScreen(navController: NavController,
               mainViewModel: MainViewModel = hiltViewModel()){

    val weatherData = produceState<DataOrException<Weather,Boolean,Exception>>(
        initialValue = DataOrException(loading = true)) {
        value = mainViewModel.getWeatherData(city = "Moscow")
    }.value

    if (weatherData.loading == true){
        CircularProgressIndicator()
    }
    else if (weatherData.data!= null ){
        MainScaffold(weather = weatherData.data!!,navController)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "${weather.city.name}, ${weather.city.country}",
                navController = navController,
                elevation = 5.dp
            )
        }
    ) { paddingValues ->
        MainContent(data = weather)

    }
}

@Composable
fun MainContent(data: Weather) {
    val weatherItem = data.list[0]

    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}@2x.png"

    val appBarHeight = 34.dp
    Column(Modifier.padding(top = appBarHeight, start = 16.dp, end = 16.dp)
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(64.dp))

        Text(formatDate(data.list[0].dt),  //Thu Jun 5
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp))

        Surface(modifier = Modifier.padding(5.dp)
            .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(formatDecimals(data.list[0].temp.day) + "°",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold)
                Text(weatherItem.weather[0].main,
                    fontStyle = FontStyle.Italic)
            }
        }
        HumidityWindPressureRow(weather=weatherItem)

        HorizontalDivider(color = MaterialTheme.colorScheme.primary)

        SunsetSunRiseNow(weather = data.list[0])

        Text("This Week",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight =FontWeight.Bold)

        Surface(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(size = 14.dp)
            ) {
                LazyColumn(modifier = Modifier.padding(2.dp),
                    contentPadding = PaddingValues(1.dp)) {
                    items(items = data.list) {item: WeatherItem ->
                        WeatherItemDetailsRow(weather = item)

                    }
                }
            }
    }

}

@Composable
fun WeatherItemDetailsRow(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png"
    Surface(modifier = Modifier
        .padding(2.dp)
        .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp), bottomStart = CornerSize(6.dp)),
        color = Color.White) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {

            Text(formatDate(weather.dt).split(",")[0],
                modifier = Modifier.padding(start = 5.dp)
                )

            WeatherStateImage(imageUrl=imageUrl)

            Surface(modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)) {
                Text(weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            Text(buildAnnotatedString {
                withStyle(style= SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold)){
                    append(formatDecimals(weather.temp.max) +"°")
                }
                withStyle(style = SpanStyle(
                    color = Color.Gray)){
                    append(formatDecimals(weather.temp.min)+"°")
                }
            })
        }
    }
}

@Composable
fun SunsetSunRiseNow(weather: WeatherItem) {
    Row(Modifier.fillMaxWidth()
        .padding(top = 14.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Row() {
            Image(painter = painterResource(id = R.drawable.sun),
                contentDescription = "sunrise",
                modifier = Modifier.size(25.dp))
            Text(formatDateTime(weather.sunrise),
                style = MaterialTheme.typography.bodyLarge
                )
        }
        Row() {
            Image(painter = painterResource(id = R.drawable.night),
                contentDescription = "sunset",
                modifier = Modifier.size(25.dp))
            Text(text= formatDateTime(weather.sunset),
                style = MaterialTheme.typography.bodyLarge

            )
        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
    Row(modifier = Modifier.padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp))
            Text("${weather.humidity}%",
                style = MaterialTheme.typography.labelLarge)
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(24.dp))
            Text("${weather.pressure} psi")
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.windrapid),
                contentDescription = "Wind icon",
                modifier = Modifier.size(24.dp))
            Text("${weather.humidity} mph")
        }

    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    val painter = rememberAsyncImagePainter(
        model = imageUrl
    )
    Image(
        painter = painter,
        contentDescription = "Icon Image",
        modifier = Modifier.size(100.dp),
    )
}


