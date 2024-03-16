package com.example.movielistapp.data.local

import com.example.movielistapp.data.local.dao.MovieDao
import com.example.movielistapp.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {

    suspend fun saveMovie(movie: MovieEntity)

    suspend fun deleteMovie(movie: MovieEntity)

    fun getMovies(): Flow<List<MovieEntity>>

}

class MovieLocalDataSourceImpl(
    private val movieDao: MovieDao
): MovieLocalDataSource {
    override suspend fun saveMovie(movie: MovieEntity) {
        movieDao.insertMovie(movie)
    }

    override suspend fun deleteMovie(movie: MovieEntity) {
        movieDao.deleteMovie(movie)
    }

    override fun getMovies(): Flow<List<MovieEntity>> {
        return movieDao.getAllMovies()
    }
}