package com.example.movielistapp.data.repository

import com.example.movielistapp.data.local.MovieLocalDataSource
import com.example.movielistapp.data.local.entities.MovieEntity
import com.example.movielistapp.data.local.entities.asMovie
import com.example.movielistapp.data.model.Movie
import com.example.movielistapp.data.network.MovieRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource ,
    private val movieLocalDataSource: MovieLocalDataSource
): MovieRepository {
    override suspend fun fetchRemotePopularMovies(): List<Movie> {
        return movieRemoteDataSource.fetchPopularMovies()

    }

    override suspend fun fetchRemoteUpcomingMovies(): List<Movie> {
        return movieRemoteDataSource.fetchUpcomingMovies()

    }

    override suspend fun fetchRemoteNowPlayingMovies(): List<Movie> {
        return movieRemoteDataSource.fetchNowPlayingMovies()
    }

    override suspend fun fetchRemoteMovieById(id: Int): Movie {
        return movieRemoteDataSource.fetchMovieById(id)
    }

    override suspend fun fetchMoviesByQuery(query: String): List<Movie> {
        return movieRemoteDataSource.fetchMoviesByQuery(query)
    }

    override suspend fun saveLocalMovie(movie: MovieEntity) {
        movieLocalDataSource.saveMovie(movie)
    }

    override suspend fun deleteLocalMovie(movie: MovieEntity) {
        movieLocalDataSource.deleteMovie(movie)
    }

    override fun getLocalMovies(): Flow<List<Movie>> {
        return movieLocalDataSource.getMovies()
            .map { it.map(MovieEntity::asMovie) }
    }
}