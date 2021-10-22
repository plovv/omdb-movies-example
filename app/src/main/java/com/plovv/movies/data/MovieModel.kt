package com.plovv.movies.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity()
data class MovieModel(
    @PrimaryKey val imdbID: String,
    val Title: String,
    val Type: String,
    val Year: String,
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    @Json(name = "Poster") val PosterSrc: String,
    val Plot: String
)