package com.example.movielistapp.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.movielistapp.data.repository.MovieRepository
import com.example.movielistapp.data.model.NetworkMovie
import com.example.movielistapp.di.CustomHandler
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor (
    private val movieRepository: MovieRepository ,
    handler: CustomHandler
): ViewModel() {

    private val movieObj = handler.savedStateHandle.get<String>("movie")
    private val movie = Gson().fromJson(movieObj, NetworkMovie::class.java)!!

    val uiState: NetworkMovie by mutableStateOf(movie)
}
