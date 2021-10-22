package com.plovv.movies.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.plovv.movies.databinding.FragmentMovieSearchResultBinding
import com.plovv.movies.utils.DETAILS_CALLER
import com.plovv.movies.utils.MoviesAdapter


class MovieSearchResultFragment : Fragment() {

    private val viewModel: MovieSearchResultViewModel by lazy {
        val searchTerm: String = MovieSearchResultFragmentArgs.fromBundle(requireArguments()).searchTerm
        val viewModelFactory = MovieSearchResultViewModelFactory(searchTerm)

        ViewModelProvider(this, viewModelFactory).get(MovieSearchResultViewModel::class.java)
    }

    private lateinit var binding: FragmentMovieSearchResultBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieSearchResultBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initRecyclerView()
        initObservers()

        return binding.root
    }

    private fun initObservers() {
        viewModel.navigateToMovieDetails.observe(viewLifecycleOwner, { imdbID ->
            if(imdbID?.isNotEmpty() == true){
                this.findNavController().navigate(MovieSearchResultFragmentDirections.actionMovieSearchResultFragmentToMovieDetailsFragment(
                    imdbID,
                    DETAILS_CALLER.SEARCH_FRAG
                ))
                viewModel.completeNavigateToDetails()
            }
        })

        viewModel.response.observe(viewLifecycleOwner, Observer { response ->
            Toast.makeText(this.context, response, Toast.LENGTH_LONG).show()
        })
    }

    private fun initRecyclerView() {
        val adapter = MoviesAdapter(MoviesAdapter.MovieClickListener {
            viewModel.onMovieClick(it)
        })

        binding.listSearchResults.adapter = adapter

        viewModel.results.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

}