package com.example.movielistapp.data.local.db

import com.example.movielistapp.data.network.model.MovieDto
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {

    suspend fun saveMovie(movie: MovieDto)

    suspend fun deleteMovie(movie: MovieDto)

    fun getMovies(): Flow<List<MovieDto>>

}

class MovieLocalDataSourceImpl(
    private val movieDao: MovieDao
): MovieLocalDataSource {
    override suspend fun saveMovie(movie: MovieDto) {
        movieDao.insertMovie(movie)
    }

    override suspend fun deleteMovie(movie: MovieDto) {
        movieDao.deleteMovie(movie)
    }

    override fun getMovies(): Flow<List<MovieDto>> {
        return movieDao.getAllMovies()
    }
}