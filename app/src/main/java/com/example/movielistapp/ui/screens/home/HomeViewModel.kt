package com.example.movielistapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielistapp.data.model.Movie
import com.example.movielistapp.data.model.asEntity
import com.example.movielistapp.data.repository.MovieRepository
import com.example.movielistapp.di.CustomHandler
import com.example.movielistapp.domain.BookmarkMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor (
    private val movieRepository: MovieRepository ,
    private val bookmarkMovieUseCase: BookmarkMovieUseCase ,
    private val handler: CustomHandler
): ViewModel() {

    private var _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        refreshMovies()
    }

    fun refreshMovies(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            try {
                _uiState.value = HomeUiState.Success(
                    popularMovies = movieRepository.fetchRemotePopularMovies(),
                    playingNowMovies = movieRepository.fetchRemoteNowPlayingMovies(),
                    upcomingMovies = movieRepository.fetchRemoteUpcomingMovies()
                )
            } catch (e: IOException) {
                e.printStackTrace()
                _uiState.value = HomeUiState.Error // Reset UI state or handle the error as needed
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = HomeUiState.Error // Handle other exceptions if necessary
            }
        }
    }


    fun bookmarkMovie(movie: Movie) {
        viewModelScope.launch {
            bookmarkMovieUseCase(movie.asEntity())
        }
    }
}

sealed class HomeUiState {
    data object Error: HomeUiState()
    data object Loading: HomeUiState()
    data class Success(
        val popularMovies: List<Movie>,
        val playingNowMovies: List<Movie>,
        val upcomingMovies: List<Movie>,
    ): HomeUiState()
}
