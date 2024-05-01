package com.example.movielistapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielistapp.domain.model.Movie
import com.example.movielistapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
): ViewModel() {

    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()



    fun search(searchText: String) {
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    currentState.copy(
                        movies = movieRepository.fetchMoviesByQuery(searchText),
                        hasError = false
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update {
                    SearchUiState(hasError = true)
                }
            }
        }
    }
}

data class SearchUiState(
    val movies: List<Movie> = emptyList(),
    val hasError: Boolean = false
)

