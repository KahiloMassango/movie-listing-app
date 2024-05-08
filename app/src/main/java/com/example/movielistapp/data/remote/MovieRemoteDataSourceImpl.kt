package com.example.movielistapp.data.remote

import com.example.movielistapp.data.remote.models.asDomain
import com.example.movielistapp.domain.datasource.MovieRemoteDataSource
import com.example.movielistapp.domain.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



class MovieRemoteDataSourceImpl(
    private val apiService: MovieApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): MovieRemoteDataSource {
    override suspend fun fetchPopularMovies(): List<Movie> {
        return withContext(ioDispatcher) {
            apiService.getPopularMovies().results
                .map {
                    it.asDomain().copy(
                        backdropPath = MovieApiService.IMAGE_URL + it.backdropPath,
                        posterPath = MovieApiService.IMAGE_URL + it.posterPath
                    )
                }
        }
    }

    override suspend fun fetchUpcomingMovies(): List<Movie> {
        return withContext(ioDispatcher) {
            apiService.getUpcomingMovies().results
                .map {
                    it.asDomain().copy(
                        backdropPath = MovieApiService.IMAGE_URL + it.backdropPath,
                        posterPath = MovieApiService.IMAGE_URL + it.posterPath
                    )
                }
        }
    }

    override suspend fun fetchNowPlayingMovies(): List<Movie> {
       return withContext(ioDispatcher) {
           apiService.getNowPlayingMovies().results
               .map {
                   it.asDomain().copy(
                       backdropPath = MovieApiService.IMAGE_URL + it.backdropPath,
                       posterPath = MovieApiService.IMAGE_URL + it.posterPath
                   )
               }
       }
    }

    override suspend fun fetchMovieById(id: Int): Movie {
        return withContext(ioDispatcher) {
            apiService.getMovieById(id = id)
                .let {
                    it.asDomain().copy(
                        backdropPath = MovieApiService.IMAGE_URL + it.backdropPath,
                        posterPath = MovieApiService.IMAGE_URL + it.posterPath
                    )
                }
        }
    }

    override suspend fun fetchMoviesByQuery(query: String): List<Movie> {
        return apiService.searchMovieByQuery(query).results
            .map {
                it.asDomain().copy(
                    backdropPath = MovieApiService.IMAGE_URL + it.backdropPath,
                    posterPath = MovieApiService.IMAGE_URL + it.posterPath
                )
            }
    }
}