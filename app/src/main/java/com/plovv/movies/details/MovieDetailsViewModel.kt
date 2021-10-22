package com.plovv.movies.details

import android.app.Application
import androidx.lifecycle.*
import com.plovv.movies.R
import com.plovv.movies.data.MovieModel
import com.plovv.movies.database.MovieDao
import com.plovv.movies.network.OMDbApi
import com.plovv.movies.utils.DETAILS_CALLER
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieDetailsViewModel(val imdbID: String, val caller: DETAILS_CALLER, val database: MovieDao, val app: Application): AndroidViewModel(app) {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    val movie = MutableLiveData<MovieModel>()
    val favoriteBtnText = MutableLiveData<String>()
    private var isFavorite: Boolean = false

    val displayYear = Transformations.map(movie) {
        app.applicationContext.getString(R.string.details_year, it.Year)
    }
    val displayGenre = Transformations.map(movie) {
        app.applicationContext.getString(R.string.details_genre, it.Genre)
    }
    val displayDirector = Transformations.map(movie) {
        app.applicationContext.getString(R.string.details_director, it.Director)
    }
    val displayWriter = Transformations.map(movie) {
        app.applicationContext.getString(R.string.details_writer, it.Writer)
    }
    val displayActors = Transformations.map(movie) {
        app.applicationContext.getString(R.string.details_actors, it.Actors)
    }
    val displayType = Transformations.map(movie) {
        app.applicationContext.getString(R.string.details_type, it.Type)
    }
    val displayPlot = Transformations.map(movie) {
        app.applicationContext.getString(R.string.details_plot, it.Plot)
    }


    init {
        getMovieDetails(imdbID)
        checkIfFavorite(imdbID)
    }

    private fun checkIfFavorite(imdbID: String) {
        viewModelScope.launch {
            if(database.getById(imdbID) == null) {
                favoriteBtnText.value = app.getString(R.string.btn_fav_add_text)
                isFavorite = false
            } else {
                favoriteBtnText.value = app.getString(R.string.btn_fav_remove_text)
                isFavorite = true
            }
        }
    }

    private fun getMovieDetails(imdbID: String) {
        when(caller) {
            DETAILS_CALLER.HOME_FRAG ->
                viewModelScope.launch {
                    movie.value = database.getById(imdbID)
                }

            DETAILS_CALLER.SEARCH_FRAG ->
                viewModelScope.launch {
                    try {
                        movie.value = OMDbApi.retrofitService.fetchMovie(imdbID = imdbID)
                    } catch (e: Exception) {
                        _response.value = "Failure: ${e.message}"
                    }
                }
        }
    }

    fun onFavoriteClick() {
        viewModelScope.launch {
            val favMovie = movie.value

            if(favMovie != null) {
                if (isFavorite) {
                    database.remove(favMovie.imdbID)

                    isFavorite = false
                    favoriteBtnText.value = app.getString(R.string.btn_fav_add_text)
                } else {
                    database.insert(favMovie)

                    isFavorite = true
                    favoriteBtnText.value = app.getString(R.string.btn_fav_remove_text)
                }
            }
        }
    }

}