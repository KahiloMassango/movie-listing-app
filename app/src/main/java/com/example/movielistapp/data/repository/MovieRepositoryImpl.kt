package com.example.movielistapp.data.repository

import com.example.movielistapp.data.local.MovieLocalDataSource
import com.example.movielistapp.data.local.entities.MovieEntity
import com.example.movielistapp.data.local.entities.asMovie
import com.example.movielistapp.data.model.Movie
import com.example.movielistapp.data.network.MovieRemoteDataSource
import com.example.movielistapp.data.network.models.asMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource ,
    private val movieLocalDataSource: MovieLocalDataSource
): MovieRepository {
    override suspend fun fetchRemotePopularMovies(): List<Movie> {
        return movieRemoteDataSource.fetchPopularMovies()
            .map { it.asMovie() }

    }

    override suspend fun fetchRemoteUpcomingMovies(): List<Movie> {
        return movieRemoteDataSource.fetchUpcomingMovies()
            .map { it.asMovie() }
    }

    override suspend fun fetchRemoteNowPlayingMovies(): List<Movie> {
        return movieRemoteDataSource.fetchNowPlayingMovies()
            .map { it.asMovie() }
    }

    override suspend fun fetchRemoteMovieById(id: Int): Movie {
        return movieRemoteDataSource.fetchMovieById(id).asMovie()
    }

    override suspend fun fetchMoviesByQuery(query: String): List<Movie> {
        return movieRemoteDataSource.fetchMoviesByQuery(query)
            .map { it.asMovie() }
    }

    override suspend fun saveLocalMovie(movie: MovieEntity) {
        movieLocalDataSource.saveMovieEntity(movie)
    }

    override suspend fun deleteLocalMovie(movie: MovieEntity) {
        movieLocalDataSource.deleteMovieEntity(movie)
    }


    override fun getBookmarkedMoviesStream(): Flow<List<Movie>> {
       return movieLocalDataSource.getMoviesEntityStream().map { movieEntity ->
           movieEntity.map { it.asMovie() }
       }
    }

    override fun getBookmarkedMoviesIds(): Flow<List<Int>> {
        return movieLocalDataSource.getBookmarkedMoviesIds()
    }

    override suspend fun getMovieByID(id: Int): Movie? {
        return movieLocalDataSource.getMovieById(id)?.asMovie()
    }
}