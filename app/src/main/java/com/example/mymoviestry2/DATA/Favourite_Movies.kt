package com.example.mymoviestry2.DATA

import androidx.room.Entity
import androidx.room.Ignore

@Entity(tableName = "favourite_movies")
class Favourite_Movies(
    id: Int,
    name_russian: String,
    name_original: String,
    year: Int,
    rating_kp: Double,
    description: String,
    type: String,
    big_poster: String,
    small_poster: String,
    trailer: String
) : Movie(id,
    name_russian,
    name_original,
    year,
    rating_kp,
    description,
    type,
    big_poster,
    small_poster, trailer) {

    @Ignore
    constructor(movie: Movie) : this (id = movie.id,
        name_russian = movie.name_russian,
        name_original = movie.name_original,
        year = movie.year,
        rating_kp = movie.rating_kp,
        description = movie.description,
        type = movie.type,
        big_poster = movie.big_poster,
        small_poster = movie.small_poster,
        trailer = movie.trailer)

}