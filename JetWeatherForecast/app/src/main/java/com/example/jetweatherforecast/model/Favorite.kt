package com.example.jetweatherforecast.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "fav_tbl")
data class Favorite(
    @Nonnull
    @PrimaryKey
    @ColumnInfo("city")
    val city: String,

    @ColumnInfo("country")
    val country: String,


)