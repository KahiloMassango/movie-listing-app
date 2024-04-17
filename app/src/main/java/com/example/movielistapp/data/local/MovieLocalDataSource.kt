package com.example.movielistapp.data.local

import com.example.movielistapp.data.local.dao.MovieDao
import com.example.movielistapp.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    suspend fun saveMovieEntity(movie: MovieEntity)

    suspend fun deleteMovieEntity(movie: MovieEntity)

    fun getMoviesEntityStream(): Flow<List<MovieEntity>>

    suspend fun getMovieById(id: Int): MovieEntity?

    fun getBookmarkedMoviesIds(): Flow<List<Int>>

}

class MovieLocalDataSourceImpl(
    private val movieDao: MovieDao
): MovieLocalDataSource {
    override suspend fun saveMovieEntity(movie: MovieEntity) {
        movieDao.insertMovie(movie)
    }

    override suspend fun deleteMovieEntity(movie: MovieEntity) {
        movieDao.deleteMovie(movie)
    }

    override fun getMoviesEntityStream(): Flow<List<MovieEntity>> {
        return movieDao.getBookmarkedMoviesStream()
    }

    override fun getBookmarkedMoviesIds(): Flow<List<Int>> {
        return movieDao.getBookmarkedMoviesIdsStream()
    }
    override suspend fun getMovieById(id: Int): MovieEntity? {
        return movieDao.getMovieById(id)
    }
}