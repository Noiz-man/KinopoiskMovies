package com.example.mymoviestry2.DATA

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Movie_DAO {

    @Query("SELECT * FROM movie_table")
    fun getAllmovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM favourite_movies")
    fun getAllFavouritesMovies(): LiveData<List<Favourite_Movies>>

    @Query("SELECT * FROM movie_table WHERE id == :movieid")
    fun getMovieByID(movieid: Int): Movie

    @Query("SELECT * FROM favourite_movies WHERE id == :movieid")
    fun getFavouriteMovieByID(movieid: Int): Favourite_Movies

    @Query("DELETE FROM movie_table")
    fun deleteAllMovies()

    @Delete
    fun deleteMovie(movie: Movie)

    @Delete
    fun deleteFavouriteMovie(movie: Favourite_Movies)

    @Insert
    fun insertMovie(movie: Movie)

    @Insert
    fun insertFavouriteMovie(movie: Favourite_Movies)
}