package com.plovv.movies.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.plovv.movies.database.MovieDao

class HomeViewModelFactory(private val dataSource: MovieDao): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dataSource) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}