package com.example.movielistapp.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielistapp.data.model.Movie
import com.example.movielistapp.data.model.asEntity
import com.example.movielistapp.data.repository.MovieRepository
import com.example.movielistapp.domain.BookmarkMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor (
    private val movieRepository: MovieRepository,
    private val bookmarkMovieUseCase: BookmarkMovieUseCase,
     savedStateHandler: SavedStateHandle
): ViewModel() {

    val uiState = flow {
        emit(DetailState.Loading)
        try {
            val id = savedStateHandler.get<String>("movieId")!!.toInt()
            val movie = movieRepository.getMovieByID(id) ?: movieRepository.fetchRemoteMovieById(id)
            emit(DetailState.Success(movie))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DetailState.Error(e.toString()))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DetailState.Loading)

    val bookmarkedMovies = movieRepository.getBookmarkedMoviesIds()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun bookmarkMovie(movie: Movie) {
        viewModelScope.launch {
            bookmarkMovieUseCase(movie.asEntity())
        }
    }

    fun deleteBookmarkedMovie(movie: Movie) {
        viewModelScope.launch {
            movieRepository.deleteLocalMovie(movie.asEntity())
        }
    }
}
sealed class DetailState {
    data object Loading: DetailState()
    data class Success(val movie: Movie): DetailState()
    data class Error(val error: String): DetailState()
}
