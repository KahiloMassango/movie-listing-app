package com.example.movielistapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielistapp.data.local.entities.MovieCategory
import com.example.movielistapp.domain.repository.MovieRepository
import com.example.movielistapp.domain.repository.WorkManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor (
     movieRepository: MovieRepository,
    private val workManagerRepository: WorkManagerRepository,
): ViewModel() {

    val popularMovies = movieRepository.getLocalMovieByCategoryStream(MovieCategory.POPULAR.name)
        .stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),emptyList())

    val playingNowMovies = movieRepository.getLocalMovieByCategoryStream(MovieCategory.NOW_PLAYING.name)
        .stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),emptyList())

    val upcomingMovies = movieRepository.getLocalMovieByCategoryStream(MovieCategory.UPCOMING.name)
        .stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),emptyList())

    init {
        viewModelScope.launch {
            workManagerRepository.syncDatabase()
        }
    }
}
