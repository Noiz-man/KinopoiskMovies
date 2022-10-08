package com.example.mymoviestry2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviestry2.DATA.Favourite_Movies
import com.example.mymoviestry2.DATA.Movie
import com.example.mymoviestry2.DATA.Movies_View_Model
import com.squareup.picasso.Picasso
import java.util.*

class Activity_Detail : AppCompatActivity(), View.OnClickListener {
    private lateinit var imageView: ImageView
    private lateinit var textViewTitle: TextView
    private lateinit var textViewOriginalTitle: TextView
    private lateinit var textViewRaiting: TextView
    private lateinit var textViewYear: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var viewModel: Movies_View_Model
    private lateinit var imageviewFavourite: ImageView
    private lateinit var textViewTrailer: TextView
    private lateinit var imageViewTrailer: ImageView
    private var movie: Movie? = null
    private var favouriteMovie: Favourite_Movies? = null
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        lang = Locale.getDefault().language

        imageView = findViewById(R.id.imageView)
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle)
        textViewRaiting = findViewById(R.id.textViewRaiting)
        textViewYear = findViewById(R.id.textViewYear)
        textViewDescription = findViewById(R.id.textViewDescription)
        imageviewFavourite = findViewById(R.id.imageViewFavorite)
        textViewTrailer = findViewById(R.id.textViewTrailer)
        imageViewTrailer = findViewById(R.id.imageViewTrailer)

        imageviewFavourite.setOnClickListener(this)

        val movieID = intent.getIntExtra("id", -1)
        viewModel = ViewModelProvider(this)[Movies_View_Model::class.java]
        movie = viewModel.getMovieByID(movieID)
        Picasso.get().load(movie?.big_poster).placeholder(R.drawable.placeholder).into(imageView)
        textViewTitle.text = movie?.name_russian
        textViewOriginalTitle.text = movie?.name_original
        textViewRaiting.text = movie?.rating_kp.toString()
        textViewYear.text = movie?.year.toString()
        textViewDescription.text = movie?.description
        textViewTrailer.text = "Трейлер ${movie?.name_russian}"
        imageViewTrailer.setOnClickListener(this)
        textViewTrailer.setOnClickListener(this)
        setFafourite()

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.imageViewFavorite -> {
                if (favouriteMovie == null) {
                    viewModel.inserFavouriteMovie(Favourite_Movies(movie!!))
                    Toast.makeText(this, "Добавлено в избранное", Toast.LENGTH_LONG).show()
                } else {
                    viewModel.deleteFavouriteMovie(favouriteMovie!!)
                    Toast.makeText(this, "Удалено из избранного", Toast.LENGTH_LONG).show()
                }
                setFafourite()
            }
            R.id.textViewTrailer, R.id.imageViewTrailer -> {
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(movie?.trailer))
                startActivity(intent)
            }
        }
    }

    fun setFafourite() {
        favouriteMovie = movie?.let { viewModel.getFavouriteMovieByID(it.id) }
        if (favouriteMovie == null) {
            imageviewFavourite.setImageResource(R.drawable.unlike)
        } else imageviewFavourite.setImageResource(R.drawable.like)
    }

}