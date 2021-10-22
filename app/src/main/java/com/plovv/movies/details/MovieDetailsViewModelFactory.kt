package com.plovv.movies.details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.plovv.movies.database.MovieDao
import com.plovv.movies.utils.DETAILS_CALLER

class MovieDetailsViewModelFactory(val imdbID: String, val caller: DETAILS_CALLER, val database: MovieDao, val app: Application): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)){
            return MovieDetailsViewModel(imdbID, caller, database, app) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}