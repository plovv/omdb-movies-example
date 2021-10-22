package com.plovv.movies.data

import com.squareup.moshi.Json

data class SearchResultModel(
    var Response: String = "",
    var Error: String = "",
    @Json(name = "Search") val Results: List<MovieSearchData>?
)

data class MovieSearchData(
    val imdbID: String,
    val Title: String,
    val Type: String,
    val Year: String,
    val Poster: String
)