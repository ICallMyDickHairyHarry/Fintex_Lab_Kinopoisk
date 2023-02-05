package com.example.fintexlabkinopoisk.popular

import android.os.Bundle
import android.view.*
import android.widget.ListAdapter
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fintexlabkinopoisk.R
import com.example.fintexlabkinopoisk.databinding.FragmentPopularBinding

class PopularFragment : Fragment() {

    private val viewModel: PopularViewModel by activityViewModels()
    private lateinit var adapter: FilmLinearAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPopularBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        binding.retryButton.setOnClickListener { viewModel.getPopularFilms() }
        binding.filmsList.adapter = FilmLinearAdapter(FilmListener { film ->
            viewModel.onFilmClicked(film.filmId)
            // filmName to set the app bar title, filmId to be able to retry request on network error
            val action = PopularFragmentDirections.actionPopularFragment2ToFilmDetailFragment(
                filmName = film.nameRu, filmId = film.filmId)
            // Navigate using that action
            findNavController().navigate(action)
        })

        adapter = binding.filmsList.adapter as FilmLinearAdapter



        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)

        val searchButton = menu.findItem(R.id.search).actionView as SearchView?
        searchButton?.queryHint = "Введите текст"
        searchButton?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if(newText.isNullOrEmpty()){
                    adapter.submitList(viewModel.films.value)
                } else {
                    adapter.submitList(viewModel.filter(newText))
                }

                return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)

    }

}