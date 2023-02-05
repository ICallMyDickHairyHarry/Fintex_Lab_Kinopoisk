package com.example.fintexlabkinopoisk.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fintexlabkinopoisk.databinding.FragmentFilmDetailBinding

class FilmDetailFragment : Fragment() {

    private val viewModel: PopularViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFilmDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val filmId = arguments?.getInt("filmId")

        binding.retryButton.setOnClickListener {
            filmId?.let {
                viewModel.getSelectedFilm(it)
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}