package com.example.mymoviestry2.DATA

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class Movies_View_Model(application: Application) : AndroidViewModel(application) {
    val database = Movie_DataBase.getInstance(application)
    val movies: LiveData<List<Movie>> = database.movieDAO().getAllmovies()
    val favouriteMovies: LiveData<List<Favourite_Movies>> = database.movieDAO().getAllFavouritesMovies()


    fun insertMovie (movie: Movie) {
        val insertThread = Thread{
            database.movieDAO().insertMovie(movie)
        }
        insertThread.start()
        insertThread.join()
    }

    fun inserFavouriteMovie (movie: Favourite_Movies) {
        val insertThread = Thread{
            database.movieDAO().insertFavouriteMovie(movie)
        }
        insertThread.start()
        insertThread.join()
    }

    fun getMovieByID (id: Int): Movie? {
        var movie: Movie? = null
        val getMovieThread = Thread{
            movie = database.movieDAO().getMovieByID(id)
        }
        getMovieThread.start()
        getMovieThread.join()
        return movie
    }

    fun getFavouriteMovieByID (id: Int): Favourite_Movies? {
        var movie: Favourite_Movies? = null
        val getMovieThread = Thread{
            movie = database.movieDAO().getFavouriteMovieByID(id)
        }
        getMovieThread.start()
        getMovieThread.join()
        return movie
    }

    fun deleteMovie (movie: Movie) {
        val deleteThread = Thread{
            database.movieDAO().deleteMovie(movie)
        }
        deleteThread.start()
    }

    fun deleteFavouriteMovie (movie: Favourite_Movies) {
        val deleteThread = Thread{
            database.movieDAO().deleteFavouriteMovie(movie)
        }
        deleteThread.start()
        deleteThread.join()
    }

    fun deleteAll () {
        val deleteAllThread = Thread{
            database.movieDAO().deleteAllMovies()
        }
        deleteAllThread.start()
        deleteAllThread.join()
    }

}