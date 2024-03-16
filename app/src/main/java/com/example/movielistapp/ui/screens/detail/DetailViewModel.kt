package com.example.movielistapp.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielistapp.data.MovieRepository
import com.example.movielistapp.data.model.Movie
import com.example.movielistapp.data.model.toMovieDto
import com.example.movielistapp.di.CustomHandler
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor (
    private val movieRepository: MovieRepository,
    handler: CustomHandler
): ViewModel() {

    private val movieObj = handler.savedStateHandle.get<String>("movie")
    private val movie = Gson().fromJson(movieObj, Movie::class.java)!!

    val uiState: Movie by mutableStateOf(movie)

    fun saveMovie(movie: Movie) {
        viewModelScope.launch {
            movieRepository.saveLocalMovie(movie.toMovieDto())
        }
    }
}
