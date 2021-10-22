package com.plovv.movies.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.plovv.movies.data.MovieModel

@Dao
interface MovieDao {

    // Room uses a background thread for the specific @Query which returns LiveData
    @Query("select * from MovieModel")
    fun getAll(): LiveData<List<MovieModel>>

    @Query("select * from MovieModel where imdbID = :id")
    suspend fun getById(id: String): MovieModel?

    @Insert
    suspend fun insert(movie: MovieModel)

    @Query("delete from MovieModel where imdbID = :id")
    suspend fun remove(id: String)

}