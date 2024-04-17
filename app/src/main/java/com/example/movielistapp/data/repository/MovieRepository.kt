package com.example.movielistapp.data.repository

import com.example.movielistapp.data.local.entities.MovieEntity
import com.example.movielistapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun fetchRemotePopularMovies(): List<Movie>

    suspend fun fetchRemoteUpcomingMovies(): List<Movie>

    suspend fun fetchRemoteNowPlayingMovies(): List<Movie>

    suspend fun fetchRemoteMovieById(id: Int): Movie

    suspend fun fetchMoviesByQuery(query: String): List<Movie>

    suspend fun saveLocalMovie(movie: MovieEntity)

    suspend fun deleteLocalMovie(movie: MovieEntity)

    suspend fun getMovieByID(id: Int): Movie?

    fun getBookmarkedMoviesStream(): Flow<List<Movie>>

    fun getBookmarkedMoviesIds(): Flow<List<Int>>
}

