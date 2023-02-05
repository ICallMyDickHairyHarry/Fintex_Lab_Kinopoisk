package com.example.fintexlabkinopoisk.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintexlabkinopoisk.network.Film
import com.example.fintexlabkinopoisk.network.KinopoiskApi
import com.example.fintexlabkinopoisk.network.SpecificFilm
import kotlinx.coroutines.launch


enum class KinopoiskApiStatus { LOADING, ERROR, DONE }
private const val topFilmType = "TOP_100_POPULAR_FILMS"

/**
 * The [ViewModel] that is attached to the [PopularFragment].
 */
class PopularViewModel : ViewModel() {

    // network status value for popular fragment
    private val _status = MutableLiveData<KinopoiskApiStatus>()
    val status: LiveData<KinopoiskApiStatus> = _status

    // The internal MutableLiveData that stores the status of the most recent request
    private val _films = MutableLiveData<List<Film>>()
    // The external immutable LiveData for the request status
    val films: LiveData<List<Film>> = _films

    private val _film = MutableLiveData<SpecificFilm>()
    val film : LiveData<SpecificFilm> = _film

    // network status value for detail fragment
    private val _statusDetail = MutableLiveData<KinopoiskApiStatus>()
    val statusDetail: LiveData<KinopoiskApiStatus> = _statusDetail

    /**
     * Call getPopularFilms() on init so we can display status immediately.
     */
    init {
        getPopularFilms()
    }

    /**
     * Gets Films information from the Kinopoisk API Retrofit service and updates the
     * [Film] [List] [LiveData].
     */
    fun getPopularFilms() {
        viewModelScope.launch {
            _status.value = KinopoiskApiStatus.LOADING
            try {
                _films.value = KinopoiskApi.retrofitService
                    .getTopFilms(topFilmType).films
                _status.value = KinopoiskApiStatus.DONE
            } catch (e: Exception) {
                _status.value = KinopoiskApiStatus.ERROR
                _films.value = listOf()
            }
        }
    }

    fun onFilmClicked(filmId: Int) {
        getSelectedFilm(filmId)
    }

    fun getSelectedFilm(filmId: Int) {
        viewModelScope.launch {
            _statusDetail.value = KinopoiskApiStatus.LOADING
            try {
                _film.value = KinopoiskApi.retrofitService
                    .getSpecificFilm(filmId)
                _statusDetail.value = KinopoiskApiStatus.DONE
            } catch (e: Exception) {
                _statusDetail.value = KinopoiskApiStatus.ERROR
            }
        }
    }

    fun filter(query: CharSequence): List<Film> {
        return films.value?.filter { it.nameRu.contains(query) } ?: listOf()
    }
}