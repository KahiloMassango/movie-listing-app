package com.example.movielistapp.data

import com.example.movielistapp.data.model.Movie
import com.example.movielistapp.data.network.RemoteDataSource

interface MovieRepository {
    suspend fun fetchRemotePopularMovies(): List<Movie>

    suspend fun fetchRemoteUpcomingMovies(): List<Movie>

    suspend fun fetchRemoteNowPlayingMovies(): List<Movie>

    suspend fun fetchRemoteMovieById(id: Int): Movie

    suspend fun fetchMoviesByQuery(query: String): List<Movie>
}

class MovieRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): MovieRepository {
    override suspend fun fetchRemotePopularMovies(): List<Movie> {
        return remoteDataSource.fetchPopularMovies()

    }

    override suspend fun fetchRemoteUpcomingMovies(): List<Movie> {
        return remoteDataSource.fetchUpcomingMovies()

    }

    override suspend fun fetchRemoteNowPlayingMovies(): List<Movie> {
        return remoteDataSource.fetchNowPlayingMovies()
    }

    override suspend fun fetchRemoteMovieById(id: Int): Movie {
        return remoteDataSource.fetchMovieById(id)
    }

    override suspend fun fetchMoviesByQuery(query: String): List<Movie> {
        return remoteDataSource.fetchMoviesByQuery(query)
    }

}
