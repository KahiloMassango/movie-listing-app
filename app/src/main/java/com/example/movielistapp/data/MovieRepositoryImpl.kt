package com.example.movielistapp.data

import com.example.movielistapp.data.local.db.MovieLocalDataSource
import com.example.movielistapp.data.network.MovieRemoteDataSource
import com.example.movielistapp.data.network.model.Movie
import com.example.movielistapp.data.network.model.MovieDto
import kotlinx.coroutines.flow.Flow


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

    override suspend fun saveLocalMovie(movie: MovieDto) {
        movieLocalDataSource.saveMovie(movie)
    }

    override suspend fun deleteLocalMovie(movie: MovieDto) {
        movieLocalDataSource.deleteMovie(movie)
    }

    override fun getLocalMovies(): Flow<List<MovieDto>> {
        return movieLocalDataSource.getMovies()
    }
}