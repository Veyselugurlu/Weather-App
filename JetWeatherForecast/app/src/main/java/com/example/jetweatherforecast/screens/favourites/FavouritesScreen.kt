package com.example.jetweatherforecast.screens.favourites

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.model.Favorite
import com.example.jetweatherforecast.navigation.WeatherScreens
import com.example.jetweatherforecast.widgets.WeatherAppBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavouritesScreen(navController: NavController,
                     favoriteViewModel: FavoriteViewModel = hiltViewModel()){
    Scaffold(topBar = { WeatherAppBar(
        title = "Favorites Cities",
        icon = Icons.Default.ArrowBack,
        isMainScreen = false,
        navController = navController){
        navController.popBackStack() }
    }){ paddingValues ->
        Surface(modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start) {
                val list = favoriteViewModel.favList.collectAsState().value

                LazyColumn() {
                    items(items = list) {
                        CityRow(it,navController = navController,favoriteViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun CityRow(
    favorite: Favorite,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel) {
        Surface(Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(75.dp)
            .clickable {
                navController.navigate(WeatherScreens.MainScreen.name+"/${favorite.city}")
            },
            shape = CircleShape.copy(
                topStart = CornerSize(6.dp),
                topEnd = CornerSize(6.dp)),
            color= Color(0xFFB2DFDB)) {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                    Text(favorite.city, modifier = Modifier.padding(start = 5.dp))

                    Surface(modifier = Modifier
                        .padding(0.dp),
                        shape = CircleShape,
                        color = Color(0xFFD1E3E1)) {
                        Text(text = favorite.country,
                            modifier = Modifier.padding(4.dp),
                            style = MaterialTheme.typography.labelLarge
                            )
                    }

                    Icon(Icons.Default.Delete,"Delete Icon",
                        modifier = Modifier.clickable {
                            favoriteViewModel.deleteFavorite(favorite)
                        },
                        tint = Color.Red.copy(0.3f)
                        )
                }
        }
}
