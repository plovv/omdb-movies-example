package com.plovv.movies.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plovv.movies.database.MovieDao

class HomeViewModel(database: MovieDao): ViewModel() {

    private val _clearEnabled = MutableLiveData<Boolean>()
    val clearEnabled: LiveData<Boolean>
        get() = _clearEnabled

    private val _clearInput = MutableLiveData<Boolean>()
    val clearInput: LiveData<Boolean>
        get() = _clearInput
    fun endClearInput() = run { _clearInput.value = false }

    private val _navigateToSearchResult = MutableLiveData<String>()
    val navigateToShowSearchResult: LiveData<String>
        get() = _navigateToSearchResult
    fun completeNavigateToSearchResult() = run { _navigateToSearchResult.value = null }

    private val _navigateToSelectedMovieDetails = MutableLiveData<String>()
    val navigateToMovieDetails: LiveData<String>
        get() = _navigateToSelectedMovieDetails
    fun completeNavigateToDetails() = run { _navigateToSelectedMovieDetails.value = null }

    private val _emptyTerm = MutableLiveData<Boolean>()
    val emptyTerm: LiveData<Boolean>
        get() = _emptyTerm
    fun endEmptyTerm() = run { _emptyTerm.value = false }

    private var _searchTerm: String?
    val movies = database.getAll()

    init {
        _clearEnabled.value = false
        _clearInput.value = false
        _searchTerm = null
        _emptyTerm.value = false
    }

    fun onTextChange(newText: String) {
        _clearEnabled.value = newText.isNotEmpty()
        _searchTerm = newText
    }

    fun onClearClick() {
        _clearInput.value = true
    }

    fun onSearchClick() {
        _navigateToSearchResult.value = _searchTerm

        _emptyTerm.value = (_searchTerm?: "").isEmpty()
    }

    fun onMovieClick(imdbID: String) {
        _navigateToSelectedMovieDetails.value = imdbID
    }

}