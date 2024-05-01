package com.example.movielistapp.data.local

import com.example.movielistapp.data.local.entities.MovieEntity
import com.example.movielistapp.data.local.entities.asDomain
import com.example.movielistapp.domain.datasource.MovieRemoteDataSource
import com.example.movielistapp.domain.model.Movie
import com.example.movielistapp.playingMovieTest
import com.example.movielistapp.popularMovieTest
import com.example.movielistapp.upcomingMovieTest

class FakeRemoteDataSource(
    private var moviesList: MutableList<MovieEntity> = mutableListOf(
        popularMovieTest,
        upcomingMovieTest,
        playingMovieTest
    )
): MovieRemoteDataSource {

    override suspend fun fetchPopularMovies(): List<Movie> {
        return listOf(popularMovieTest.asDomain())
    }

    override suspend fun fetchUpcomingMovies(): List<Movie> {
        return listOf(upcomingMovieTest.asDomain())
    }

    override suspend fun fetchNowPlayingMovies(): List<Movie> {
        return listOf(playingMovieTest.asDomain())
    }

    override suspend fun fetchMovieById(id: Int): Movie {
        val movie = moviesList.find { movie -> movie.id == id }!!
        return movie.asDomain()
    }

    override suspend fun fetchMoviesByQuery(query: String): List<Movie> {
        val filteredMovies = moviesList.filter { movie ->
            movie.title.contains(query, ignoreCase = true)
        }.map { it.asDomain() }
        return filteredMovies
    }
}