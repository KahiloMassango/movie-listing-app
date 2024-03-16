package com.example.movielistapp.data.network

import com.example.movielistapp.data.network.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface MovieRemoteDataSource {
    suspend fun fetchPopularMovies(): List<Movie>

    suspend fun fetchUpcomingMovies(): List<Movie>

    suspend fun fetchNowPlayingMovies(): List<Movie>

    suspend fun fetchMovieById(id: Int): Movie

    suspend fun fetchMoviesByQuery(query: String): List<Movie>
}

class MovieRemoteDataSourceImpl(
    private val apiService: MovieApiService
): MovieRemoteDataSource {
    override suspend fun fetchPopularMovies(): List<Movie> {
        return withContext(Dispatchers.IO) {
            apiService.getPopularMovies().results
                .map {
                    it.copy(
                        backdropPath = MovieApiService.ImageUrl + it.backdropPath,
                        posterPath = MovieApiService.ImageUrl + it.posterPath
                    )
                }
        }
    }

    override suspend fun fetchUpcomingMovies(): List<Movie> {
        return withContext(Dispatchers.IO) {
            apiService.getUpcomingMovies().results
                .map {
                    it.copy(
                        backdropPath = MovieApiService.ImageUrl + it.backdropPath,
                        posterPath = MovieApiService.ImageUrl + it.posterPath
                    )
                }
        }
    }

    override suspend fun fetchNowPlayingMovies(): List<Movie> {
       return withContext(Dispatchers.IO) {
           apiService.getNowPlayingMovies().results
               .map {
                   it.copy(
                       backdropPath = MovieApiService.ImageUrl + it.backdropPath,
                       posterPath = MovieApiService.ImageUrl + it.posterPath
                   )
               }
       }
    }

    override suspend fun fetchMovieById(id: Int): Movie {
        return withContext(Dispatchers.IO) {
            apiService.getMovieById(id = id)
                .let {
                    it.copy(
                        backdropPath = MovieApiService.ImageUrl + it.backdropPath,
                        posterPath = MovieApiService.ImageUrl + it.posterPath
                    )
                }
        }
    }

    override suspend fun fetchMoviesByQuery(query: String): List<Movie> {
        return apiService.searchMovieByQuery(query).results
            .map {
                it.copy(
                    backdropPath = MovieApiService.ImageUrl + it.backdropPath,
                    posterPath = MovieApiService.ImageUrl + it.posterPath
                )
            }
    }
}