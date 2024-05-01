package com.example.movielistapp.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielistapp.domain.model.Movie
import com.example.movielistapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor (
    private val movieRepository: MovieRepository,
    savedStateHandler: SavedStateHandle
): ViewModel() {

    val uiState = flow {
        emit(DetailState.Loading)
        try {
            val movieId = savedStateHandler.get<String>("movieId")!!.toInt()
            val movie = getMovie(movieId)
            emit(DetailState.Success(movie))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DetailState.Error(e.toString()))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),DetailState.Loading)

    private suspend fun getMovie(id: Int): Movie {
        return movieRepository.getLocalMovieByID(id)
            ?: movieRepository.fetchRemoteMovieById(id)
    }
}
sealed class DetailState {
    data object Loading: DetailState()
    data class Success(val movie: Movie): DetailState()
    data class Error(val error: String): DetailState()
}
