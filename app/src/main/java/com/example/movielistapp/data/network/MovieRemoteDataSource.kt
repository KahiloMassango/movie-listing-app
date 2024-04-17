package com.example.movielistapp.data.network

import com.example.movielistapp.data.network.models.NetworkMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface MovieRemoteDataSource {
    suspend fun fetchPopularMovies(): List<NetworkMovie>

    suspend fun fetchUpcomingMovies(): List<NetworkMovie>

    suspend fun fetchNowPlayingMovies(): List<NetworkMovie>

    suspend fun fetchMovieById(id: Int): NetworkMovie

    suspend fun fetchMoviesByQuery(query: String): List<NetworkMovie>
}

class MovieRemoteDataSourceImpl(
    private val apiService: MovieApiService
): MovieRemoteDataSource {
    override suspend fun fetchPopularMovies(): List<NetworkMovie> {
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

    override suspend fun fetchUpcomingMovies(): List<NetworkMovie> {
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

    override suspend fun fetchNowPlayingMovies(): List<NetworkMovie> {
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

    override suspend fun fetchMovieById(id: Int): NetworkMovie {
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

    override suspend fun fetchMoviesByQuery(query: String): List<NetworkMovie> {
        return apiService.searchMovieByQuery(query).results
            .map {
                it.copy(
                    backdropPath = MovieApiService.ImageUrl + it.backdropPath,
                    posterPath = MovieApiService.ImageUrl + it.posterPath
                )
            }
    }
}