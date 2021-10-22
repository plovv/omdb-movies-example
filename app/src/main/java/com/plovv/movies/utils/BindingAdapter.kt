package com.plovv.movies.utils

import android.app.Application
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.plovv.movies.R
import com.plovv.movies.data.MovieModel

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.ic_img_container)
                .error(R.drawable.ic_image_missing))
            .into(imgView)
    }
}

@BindingAdapter("resultYearText")
fun TextView.resultYear(movie: MovieModel?) {
    movie?.let {
        text = context.getString(R.string.list_item_year, movie.Year)
    }
}