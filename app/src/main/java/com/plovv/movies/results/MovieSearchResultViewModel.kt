package com.plovv.movies.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plovv.movies.data.MovieModel
import com.plovv.movies.data.SearchResultModel
import com.plovv.movies.network.OMDbApi
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieSearchResultViewModel(val searchTerm: String): ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _results = MutableLiveData<List<MovieModel>>()
    val results: LiveData<List<MovieModel>>
        get() = _results

    private val _navigateToSelectedMovieDetails = MutableLiveData<String>()
    val navigateToMovieDetails: LiveData<String>
        get() = _navigateToSelectedMovieDetails
    fun completeNavigateToDetails() = run { _navigateToSelectedMovieDetails.value = null }

    init {
        getResults()
    }

    private fun getResults() {
        viewModelScope.launch {
            try {
                val apiResponse: SearchResultModel = OMDbApi.retrofitService.searchMovie(searchTerm = searchTerm)

                if (apiResponse.Response == "True") {
                    _results.value = apiResponse.Results?.map {
                        MovieModel(
                            it.imdbID,
                            it.Title,
                            it.Type,
                            it.Year,
                            "",
                            "",
                            "",
                            "",
                            it.Poster,
                            ""
                        )
                    }
                } else {
                    _response.value = apiResponse.Error
                }
            } catch (e: Exception) {
                _response.value = "Error: ${e.message}"
            }
        }
    }

    fun onMovieClick(imdbID: String) {
        _navigateToSelectedMovieDetails.value = imdbID
    }

}