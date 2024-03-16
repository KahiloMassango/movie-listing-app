package com.example.movielistapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielistapp.data.MovieRepository
import com.example.movielistapp.data.network.model.Movie
import com.example.movielistapp.di.CustomHandler
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor (
    private val movieRepository: MovieRepository,
    private val handler: CustomHandler
): ViewModel() {

    private var _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        refreshMovies()
    }

    private fun refreshMovies() {
        viewModelScope.launch {
            val popularMovies = async { movieRepository.fetchRemotePopularMovies() }
            val playingNowMovies = async { movieRepository.fetchRemoteNowPlayingMovies() }
            val upcomingMovies = async { movieRepository.fetchRemoteUpcomingMovies() }

            awaitAll(popularMovies, playingNowMovies, upcomingMovies)

            _uiState.update {
                it.copy(
                    popularMovies = popularMovies.await(),
                    playingNowMovies = playingNowMovies.await(),
                    upcomingMovies = upcomingMovies.await()
                )
            }
        }
    }

    fun parseMovieToDetail(movieObj: Movie) {
        viewModelScope.launch {
            val movie = Gson().toJson(movieObj)
            handler.savedStateHandle["movie"] = movie
        }
    }
}

data class HomeUiState(
    val popularMovies: List<Movie> = emptyList() ,
    val playingNowMovies: List<Movie> = emptyList() ,
    val upcomingMovies: List<Movie> = emptyList()
)

