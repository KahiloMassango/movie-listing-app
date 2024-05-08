package com.example.movielistapp.data.local

import com.example.movielistapp.data.local.entities.MovieEntity
import com.example.movielistapp.data.local.entities.asDomain
import com.example.movielistapp.domain.datasource.MovieLocalDataSource
import com.example.movielistapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalDataSource : MovieLocalDataSource {
    private var moviesList: MutableList<MovieEntity> = mutableListOf()
    override fun getMovieByCategoryStream(category: String): Flow<List<Movie>> {
        val filteredMovies = moviesList.filter { movie -> movie.category == category}
        return flow {
            emit(filteredMovies.map { movie -> movie.asDomain()})
        }
    }

    override suspend fun saveMovies(movies: List<MovieEntity>) {
            moviesList.addAll(movies)
    }

    override suspend fun getMovieById(id: Int): Movie? {
        return moviesList.find { it.id == id }?.asDomain()
    }
}
