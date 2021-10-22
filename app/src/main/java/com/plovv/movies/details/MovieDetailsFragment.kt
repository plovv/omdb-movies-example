package com.plovv.movies.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.plovv.movies.database.MovieDao
import com.plovv.movies.database.MovieDatabase
import com.plovv.movies.databinding.FragmentMovieDetailsBinding
import com.plovv.movies.utils.DETAILS_CALLER

class MovieDetailsFragment : Fragment() {

    private val viewModel: MovieDetailsViewModel by lazy {
        val imdbID: String = MovieDetailsFragmentArgs.fromBundle(requireArguments()).imdbID
        val caller: DETAILS_CALLER = MovieDetailsFragmentArgs.fromBundle(requireArguments()).caller
        val database: MovieDao = MovieDatabase.getInstance(requireNotNull(this.context)).movieDao

        val viewModelFactory = MovieDetailsViewModelFactory(imdbID, caller, database, requireNotNull(activity).application)

        ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel::class.java)
    }

    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.response.observe(viewLifecycleOwner, Observer { response ->
            Toast.makeText(this.context, response, Toast.LENGTH_LONG).show()
        })

        return binding.root
    }

}