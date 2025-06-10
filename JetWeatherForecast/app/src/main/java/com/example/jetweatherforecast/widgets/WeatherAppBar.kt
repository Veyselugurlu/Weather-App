package com.example.jetweatherforecast.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.model.Favorite
import com.example.jetweatherforecast.navigation.WeatherScreens
import com.example.jetweatherforecast.screens.favourites.FavoriteViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon : ImageVector? = null,

    isMainScreen: Boolean = true,
    elevation : Dp = 0.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onButtonClicked: () -> Unit = {}){

    val showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        ShowSettingDropMenu(
            expanded = showDialog.value,
            onDismiss = { showDialog.value = false },
            navController = navController
        )
    }

    TopAppBar(
        title = {
            Text(text = title,
                color = MaterialTheme.colorScheme.secondary,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp)
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = {
                    onAddActionClicked.invoke()
                })
                {
                    Icon(Icons.Default.Search,
                        "search icon")
                }
                IconButton(onClick = {
                   showDialog.value = true

                }) {
                    Icon(Icons.Default.MoreVert,
                        "More Icon")
                }
            }
            else Box {}
        },
        navigationIcon = {
            if (icon != null){
                Icon(imageVector = icon,
                    null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    }
                    )
            }
            if (isMainScreen){
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite Icon",
                    modifier = Modifier.scale(0.9f)
                        .clickable {
                            val dataList = title.split(",")
                        favoriteViewModel
                            .insertFavorite(Favorite(
                                city = dataList[0],     //city name
                                country = dataList[1],  //country code
                                ))
                    }, tint = Color.Red.copy(alpha = 0.6f))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.shadow(elevation = elevation),
    )

}
@Composable
fun ShowSettingDropMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    navController: NavController
) {
    val items = listOf("About", "Favourites", "Settings")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss,
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEach { text ->
                DropdownMenuItem(
                    onClick = {
                        onDismiss()
                    },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = when (text) {
                                    "About" -> Icons.Default.Info
                                    "Favourites" -> Icons.Default.FavoriteBorder
                                    else -> Icons.Default.Settings
                                },
                                contentDescription = null,
                                tint = Color.LightGray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = text,
                                modifier = Modifier.clickable {
                                    navController.navigate( when (text) {
                                        "About" ->  WeatherScreens.AboutScreen.name
                                        "Favourites" -> WeatherScreens.FavoriteScreen.name
                                        else -> WeatherScreens.SettingsScreen.name
                                    },)
                                },
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                )
            }
        }
    }
}

