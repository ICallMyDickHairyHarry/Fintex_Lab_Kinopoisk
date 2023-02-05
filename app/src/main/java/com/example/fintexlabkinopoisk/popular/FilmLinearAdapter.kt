package com.example.fintexlabkinopoisk.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fintexlabkinopoisk.databinding.VerticalListItemBinding
import com.example.fintexlabkinopoisk.network.Film
import java.util.*

class FilmLinearAdapter(val clickListener: FilmListener):
    ListAdapter<Film, FilmLinearAdapter.FilmViewHolder> (DiffCallback) {

    class FilmViewHolder (private var binding: VerticalListItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: FilmListener, film: Film) {
            binding.film = film
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FilmViewHolder(
            VerticalListItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val filmItem = getItem(position)
        holder.bind(clickListener, filmItem)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Film>() {

        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.filmId == newItem.filmId
        }

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.nameRu == newItem.nameRu && oldItem.posterUrlPreview == newItem.posterUrlPreview
        }
    }
}

class FilmListener(val clickFunction: (film: Film) -> Unit) {
    fun onClick(film: Film) = clickFunction(film)
}

