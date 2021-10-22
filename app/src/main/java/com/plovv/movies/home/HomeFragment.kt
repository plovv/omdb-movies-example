package com.plovv.movies.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.plovv.movies.R
import com.plovv.movies.database.MovieDatabase
import com.plovv.movies.databinding.FragmentHomeBinding
import com.plovv.movies.utils.DETAILS_CALLER
import com.plovv.movies.utils.MoviesAdapter

/**
 * First view the users see, and also were the search + favorite movies are located.
 */
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by lazy {
        val dataSource = MovieDatabase.getInstance(requireNotNull(this.context)).movieDao
        val homeViewModelFactory = HomeViewModelFactory(dataSource)

        ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initSearchInput()
        initClearInput()
        initRecyclerView()
        initObservers()

        return binding.root
    }

    private fun initRecyclerView() {
        val adapter = MoviesAdapter(MoviesAdapter.MovieClickListener {
            viewModel.onMovieClick(it)
        })

        binding.lstFavorites.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun initClearInput() {
        viewModel.clearInput.observe(viewLifecycleOwner, Observer { doClearInput ->
            if(doClearInput) {
                binding.inputSearchTerm.setText("")
                viewModel.endClearInput()
            }
        })
    }

    private fun initSearchInput() {
        binding.inputSearchTerm.addTextChangedListener {
            viewModel.onTextChange(it.toString())
        }
    }

    private fun initObservers() {
        viewModel.emptyTerm.observe(viewLifecycleOwner, Observer { isEmptyTerm ->
            if(isEmptyTerm) {
                Toast.makeText(this.context, getString(R.string.empty_search_message), Toast.LENGTH_SHORT).show()
                viewModel.endEmptyTerm()
            }
        })

        viewModel.navigateToShowSearchResult.observe(viewLifecycleOwner, Observer { searchTerm ->
            if(searchTerm?.isNotEmpty() == true) {
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovieSearchResultFragment(searchTerm))
                viewModel.completeNavigateToSearchResult()
            }
        })

        viewModel.navigateToMovieDetails.observe(viewLifecycleOwner, Observer { imdbID ->
            if(imdbID?.isNotEmpty() == true) {
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(imdbID, DETAILS_CALLER.HOME_FRAG))
                viewModel.completeNavigateToDetails()
            }
        })
    }

}
