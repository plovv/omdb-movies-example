package com.plovv.movies.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.plovv.movies.home.HomeViewModel

class MovieSearchResultViewModelFactory(val searchTerm: String):  ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieSearchResultViewModel::class.java)) {
            return MovieSearchResultViewModel(searchTerm) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}