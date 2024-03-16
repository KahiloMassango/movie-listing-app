package com.example.movielistapp.data.repository

import com.example.movielistapp.data.model.NetworkMovie
import com.example.movielistapp.data.network.RemoteDataSource

interface MovieRepository {
    suspend fun fetchRemotePopularMovies(): List<NetworkMovie>

    suspend fun fetchRemoteUpcomingMovies(): List<NetworkMovie>

    suspend fun fetchRemoteNowPlayingMovies(): List<NetworkMovie>

    suspend fun fetchRemoteMovieById(id: Int): NetworkMovie

    suspend fun fetchMoviesByQuery(query: String): List<NetworkMovie>
}

class MovieRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): MovieRepository {
    override suspend fun fetchRemotePopularMovies(): List<NetworkMovie> {
        return remoteDataSource.fetchPopularMovies()

    }

    override suspend fun fetchRemoteUpcomingMovies(): List<NetworkMovie> {
        return remoteDataSource.fetchUpcomingMovies()

    }

    override suspend fun fetchRemoteNowPlayingMovies(): List<NetworkMovie> {
        return remoteDataSource.fetchNowPlayingMovies()
    }

    override suspend fun fetchRemoteMovieById(id: Int): NetworkMovie {
        return remoteDataSource.fetchMovieById(id)
    }

    override suspend fun fetchMoviesByQuery(query: String): List<NetworkMovie> {
        return remoteDataSource.fetchMoviesByQuery(query)
    }

}
