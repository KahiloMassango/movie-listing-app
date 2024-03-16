package com.example.movielistapp.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielistapp.data.repository.MovieRepository
import com.example.movielistapp.data.model.NetworkMovie
import com.example.movielistapp.di.CustomHandler
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository ,
    private val handler: CustomHandler
): ViewModel() {

    private val _uiState: MutableStateFlow<SearchUiState> = MutableStateFlow(SearchUiState())
    val uistate = _uiState.asStateFlow()


    fun updateSearch(text: String) {
        _uiState.update {
            it.copy(searchText = text)
        }
    }

    fun search() {
        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(results = movieRepository.fetchMoviesByQuery(uistate.value.searchText))
                }
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }

    fun parseMovieToDetail(movieObj: NetworkMovie) {
        viewModelScope.launch {
            val movie = Gson().toJson(movieObj)
            handler.savedStateHandle["movie"] = movie
        }
    }
}

data class SearchUiState(
    val searchText: String = "",
    val results: List<NetworkMovie> = emptyList()
)