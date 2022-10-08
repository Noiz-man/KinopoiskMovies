package com.example.mymoviestry2.DATA

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
open class Movie (
    @PrimaryKey
    val id: Int,
    val name_russian: String,
    val name_original: String,
    val year: Int,
    val rating_kp: Double,
    val description: String,
    val type: String,
    val big_poster: String,
    val small_poster: String,
    val trailer: String

    )