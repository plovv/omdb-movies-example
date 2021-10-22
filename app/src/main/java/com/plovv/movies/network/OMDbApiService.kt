package com.plovv.movies.network

import com.plovv.movies.data.MovieModel
import com.plovv.movies.data.SearchResultModel
import com.plovv.movies.utils.API_KEY
import com.plovv.movies.utils.OMDB_API_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(OMDB_API_URL)
    .build()

interface OMDbApiService {

    @GET(".")
    suspend fun searchMovie(@Query("apikey") apiKey: String = API_KEY, @Query("s") searchTerm: String): SearchResultModel

    @GET(".")
    suspend fun fetchMovie(@Query("apikey") apiKey: String = API_KEY, @Query("i") imdbID: String): MovieModel

}

object OMDbApi {

    val retrofitService: OMDbApiService by lazy { retrofit.create(OMDbApiService::class.java) }

}