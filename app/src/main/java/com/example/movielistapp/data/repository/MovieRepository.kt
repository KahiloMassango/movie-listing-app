package com.example.movielistapp.data.repository

import com.example.movielistapp.data.model.Movie
import com.example.movielistapp.data.network.model.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchRemotePopularMovies(): List<Movie>

    suspend fun fetchRemoteUpcomingMovies(): List<Movie>

    suspend fun fetchRemoteNowPlayingMovies(): List<Movie>

    suspend fun fetchRemoteMovieById(id: Int): Movie

    suspend fun fetchMoviesByQuery(query: String): List<Movie>

    suspend fun saveLocalMovie(movie: MovieEntity)

    suspend fun deleteLocalMovie(movie: MovieEntity)

    fun getLocalMovies(): Flow<List<MovieEntity>>
}

