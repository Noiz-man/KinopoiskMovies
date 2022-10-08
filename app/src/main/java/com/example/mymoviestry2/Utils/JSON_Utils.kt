package com.example.mymoviestry2.Utils

import com.example.mymoviestry2.DATA.Movie
import org.json.JSONObject

class JSON_Utils {
    val KEY_DATA = "data"
    val KEY_ID = "id"
    val KEY_NAME_RUSSIAN = "name_russian"
    val KEY_NAME_ORIGINAL = "name_original"
    val KEY_YEAR = "year"
    val KEY_RATING_KP = "rating_kp"
    val KEY_DESCRIPTION = "description"
    val KEY_TYPE = "type"
    val KEY_BIG_POSTER = "big_poster"
    val KEY_SMALL_POSTER = "small_poster"
    val KEY_TRAILER = "trailer"

    fun getMoviesFromJSON(jsonObject: JSONObject): ArrayList<Movie> {
        val result = ArrayList<Movie>()
        val jsonArray = jsonObject.getJSONArray(KEY_DATA)
        for (i in 0 until jsonArray.length()) {
            val objectMovie: JSONObject = jsonArray.getJSONObject(i)
            val id = objectMovie.getInt(KEY_ID)
            val name_russian = objectMovie.getString(KEY_NAME_RUSSIAN)
            val name_original = objectMovie.getString(KEY_NAME_ORIGINAL)
            val year = objectMovie.getInt(KEY_YEAR)
            val rating_kp = objectMovie.getDouble(KEY_RATING_KP)
            val description = objectMovie.getString(KEY_DESCRIPTION)
            val type = objectMovie.getString(KEY_TYPE)
            val big_poster = objectMovie.getString(KEY_BIG_POSTER)
            val small_poster = objectMovie.getString(KEY_SMALL_POSTER)
            val trailerr = objectMovie.getString(KEY_TRAILER)
            val movie = Movie(id, name_russian,name_original, year, rating_kp, description,
                type, big_poster, small_poster, trailerr)
            result.add(movie)
        }
        return result
    }
}