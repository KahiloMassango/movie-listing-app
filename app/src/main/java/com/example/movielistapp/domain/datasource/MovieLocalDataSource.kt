package com.example.movielistapp.domain.datasource

import com.example.movielistapp.data.local.entities.MovieEntity
import com.example.movielistapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {

    fun getMovieByCategoryStream(category: String): Flow<List<Movie>>

    suspend fun saveMovies(movies: List<MovieEntity>)

    suspend fun getMovieById(id: Int): Movie?
}