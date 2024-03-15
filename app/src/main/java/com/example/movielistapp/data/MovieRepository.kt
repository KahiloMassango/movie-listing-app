package com.example.movielistapp.data

import com.example.movielistapp.data.network.model.Movie
import com.example.movielistapp.data.network.model.MovieDto
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchRemotePopularMovies(): List<Movie>

    suspend fun fetchRemoteUpcomingMovies(): List<Movie>

    suspend fun fetchRemoteNowPlayingMovies(): List<Movie>

    suspend fun fetchRemoteMovieById(id: Int): Movie

    suspend fun fetchMoviesByQuery(query: String): List<Movie>

    suspend fun saveLocalMovie(movie: MovieDto)

    suspend fun deleteLocalMovie(movie: MovieDto)

    fun getLocalMovies(): Flow<List<MovieDto>>
}

