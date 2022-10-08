package com.example.mymoviestry2.DATA

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Movie::class, Favourite_Movies::class], version = 2, exportSchema = false )
abstract class Movie_DataBase: RoomDatabase() {

    abstract fun movieDAO(): Movie_DAO

    companion object {
        private var database: Movie_DataBase? = null
        val DB_NAME = "Movies.db"

        @Synchronized
        fun getInstance(context: Context): Movie_DataBase {
//       метод allowMainThreadQueries() только для теста НИКОГДА ТАК НЕ ДЕЛАТЬ!!!
            if (database == null) {
                database = Room.databaseBuilder(context, Movie_DataBase::class.java, DB_NAME).fallbackToDestructiveMigration().build()
            }
            return database as Movie_DataBase
        }
    }
}