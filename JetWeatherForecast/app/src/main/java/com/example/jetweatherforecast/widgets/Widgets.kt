package com.example.jetweatherforecast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.model.WeatherItem
import formatDate
import formatDateTime
import formatDecimals


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
                color = Color(0xFFFFC400)
            ) {
                Text(weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            Text(buildAnnotatedString {
                withStyle(style= SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold)
                ){
                    append(formatDecimals(weather.temp.max) +"°")
                }
                withStyle(style = SpanStyle(
                    color = Color.Gray)
                ){
                    append(formatDecimals(weather.temp.min)+"°")
                }
            })
        }
    }
}

@Composable
fun SunsetSunRiseNow(weather: WeatherItem) {
    Row(
        Modifier.fillMaxWidth()
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


