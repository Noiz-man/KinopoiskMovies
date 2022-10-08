package com.example.mymoviestry2

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviestry2.Adapters.Movie_Adapter
import com.example.mymoviestry2.DATA.Movies_View_Model
import com.example.mymoviestry2.Utils.JSON_Utils
import com.example.mymoviestry2.Utils.Network_Utils
import java.util.*


class MainActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener,
    View.OnClickListener, Movie_Adapter.OnPosterClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: Movie_Adapter
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switch: Switch
    private lateinit var constraint_layout: ConstraintLayout
    private lateinit var black: TextView
    private lateinit var white: TextView
    private lateinit var viewModel: Movies_View_Model
    private var pageN = 1
    private lateinit var progressBar: ProgressBar
    private lateinit var lang: String

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

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lang = Locale.getDefault().language

        black = findViewById(R.id.textViewFilms)
        white = findViewById(R.id.textViewSeries)
        constraint_layout = findViewById(R.id.constraint_layout)

        progressBar = findViewById(R.id.progressBar)

        switch = findViewById(R.id.switchType)
        switch.setOnCheckedChangeListener(this)

        recyclerView = findViewById(R.id.recyclerView)
        val width = Resources.getSystem().displayMetrics.widthPixels / Resources.getSystem().displayMetrics.densityDpi
        Log.d("myTAG", width.toString())

        recyclerView.layoutManager = GridLayoutManager(this, width)
        movieAdapter = Movie_Adapter(this)
        recyclerView.adapter = movieAdapter

        black.setOnClickListener(this)
        white.setOnClickListener(this)

//        Начало базы данных Room
        viewModel = ViewModelProvider(this)[Movies_View_Model::class.java]
        downloadData(pageN)
        viewModel.movies.observe(this) {
            if (pageN == 1) {
                movieAdapter.addMovies(it)
            }
        }
//        Конец базы данных Room

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    pageN++
                    downloadData(pageN)
                }
            }
        })
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            recyclerView.setBackgroundColor(android.graphics.Color.WHITE)
            constraint_layout.setBackgroundColor(android.graphics.Color.WHITE)
            black.setTextColor(android.graphics.Color.BLACK)
            white.setTextColor(android.graphics.Color.BLACK)
        } else {
            recyclerView.setBackgroundColor(android.graphics.Color.BLACK)
            constraint_layout.setBackgroundColor(android.graphics.Color.BLACK)
            black.setTextColor(android.graphics.Color.WHITE)
            white.setTextColor(android.graphics.Color.WHITE)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.textViewFilms -> switch.isChecked = false
            R.id.textViewSeries -> switch.isChecked = true
        }
    }

    override fun onPosterClick(position: Int) {
        intent = Intent(this, Activity_Detail::class.java)
        val movie = movieAdapter.movies[position]
        intent.putExtra("id", movie.id)
        startActivity(intent)
    }

    fun downloadData(page: Int) {
        progressBar.visibility = ProgressBar.VISIBLE
        val jsonObject = Network_Utils().getJSONobject(page)
        val movies = jsonObject?.let { JSON_Utils().getMoviesFromJSON(it) }
        if (movies != null && movies.isNotEmpty()) {
            if (pageN == 1) {
                viewModel.deleteAll()
                movieAdapter.clear()
            }
            for (movie in movies) {
                viewModel.insertMovie(movie)
            }
            movieAdapter.addMovies(movies) // добавлено 16.09.22
        }
        progressBar.visibility = ProgressBar.INVISIBLE
    }



}