package com.example.mymoviestry2.Adapters

import android.annotation.SuppressLint
import android.nfc.Tag
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviestry2.DATA.Movie
import com.example.mymoviestry2.R
import com.squareup.picasso.Picasso

class Movie_Adapter(val onPosterClickListener: OnPosterClickListener) : RecyclerView.Adapter<Movie_Adapter.MovieViewHolder>() {

    val movies = ArrayList<Movie>()
    class MovieViewHolder(itemView: View, onPosterClickListener: OnPosterClickListener) : RecyclerView.ViewHolder(itemView) {
        val imageViewSmallPoster: ImageView = itemView.findViewById(R.id.imageViewSmallPoster)
        init {
            itemView.setOnClickListener {
                onPosterClickListener.onPosterClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        Log.d("myTAG", "onCreate")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_view, parent, false)
        return MovieViewHolder(view, onPosterClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        Picasso.get().load(movie.small_poster).into(holder.imageViewSmallPoster)

    }

    override fun getItemCount(): Int = movies.size

    @SuppressLint("NotifyDataSetChanged")
    fun addMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    interface OnPosterClickListener {
        fun onPosterClick(position: Int)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        this.movies.clear()
        notifyDataSetChanged()
    }
}