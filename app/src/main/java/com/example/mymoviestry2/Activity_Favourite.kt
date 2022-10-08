package com.example.mymoviestry2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviestry2.DATA.Movies_View_Model
import com.example.mymoviestry2.Adapters.Movie_Adapter

class Activity_Favourite : AppCompatActivity(), Movie_Adapter.OnPosterClickListener {
    private lateinit var recyclerViewFavourite: RecyclerView
    private lateinit var movieAdapter: Movie_Adapter
    private lateinit var viewModel: Movies_View_Model

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id) {
            R.id.item_main -> {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.item_favourite -> {
                intent = Intent(this, Activity_Favourite::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        recyclerViewFavourite = findViewById(R.id.recyclerViewFafourite)
        recyclerViewFavourite.layoutManager = GridLayoutManager(this, 2)
        movieAdapter = Movie_Adapter(this)
        recyclerViewFavourite.adapter = movieAdapter
        viewModel = ViewModelProvider(this)[Movies_View_Model::class.java]
        val favouriteMovies = viewModel.favouriteMovies
        favouriteMovies.observe(this){
            movieAdapter.addMovies(it)
        }
    }

    override fun onPosterClick(position: Int) {
        intent = Intent(this, Activity_Detail::class.java)
        val movie = movieAdapter.movies[position]
        intent.putExtra("id", movie.id)
        startActivity(intent)
    }
}