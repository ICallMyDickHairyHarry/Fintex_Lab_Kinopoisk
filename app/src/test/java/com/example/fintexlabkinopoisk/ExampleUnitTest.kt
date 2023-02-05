package com.example.fintexlabkinopoisk

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fintexlabkinopoisk.network.Film
import com.example.fintexlabkinopoisk.popular.FilmLinearAdapter
import com.example.fintexlabkinopoisk.popular.FilmListener
import com.example.fintexlabkinopoisk.popular.PopularViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.mock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val context = mock(Context::class.java)

    // Tests don't work, at least I tried
    @Test
    fun adapter_size() {
        val data = listOf(
            Film(1,"", "", listOf(),""),
            Film(1,"", "", listOf(),"")
        )
        val adapter = FilmLinearAdapter(FilmListener {})
        assertEquals("ItemAdapter is not the correct size", data.size, adapter.itemCount)
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun test_get_film() {
        val viewModel = PopularViewModel()
        viewModel.film.observeForever {}
        viewModel.getSelectedFilm(435)
        assertEquals("Зеленая миля", viewModel.film.value?.nameRu ?: 1)
    }
}