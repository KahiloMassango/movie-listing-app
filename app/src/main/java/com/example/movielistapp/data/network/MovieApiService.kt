package com.example.movielistapp.data.network

import com.example.movielistapp.BuildConfig
import com.example.movielistapp.data.network.models.NetworkMovie
import com.example.movielistapp.data.network.models.NetworkMovieResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @Headers(BuildConfig.MOVIE_API_KEY)
    @GET("movie/popular?language=en-US&page=1")
    suspend fun getPopularMovies(): NetworkMovieResponse

    @Headers(BuildConfig.MOVIE_API_KEY)
    @GET("movie/upcoming?language=en-US&page=1")
    suspend fun getUpcomingMovies(): NetworkMovieResponse

    @Headers(BuildConfig.MOVIE_API_KEY)
    @GET("movie/now_playing?language=en-US&page=1")
    suspend fun getNowPlayingMovies(): NetworkMovieResponse

    @Headers(BuildConfig.MOVIE_API_KEY)
    @GET("movie/{movieId}")
    suspend fun getMovieById(@Path("movieId") id: Int): NetworkMovie

    @Headers(BuildConfig.MOVIE_API_KEY)
    @GET("search/movie?include_adult=true")
    suspend fun searchMovieByQuery(@Query("query") query: String): NetworkMovieResponse

    companion object {
        const val ImageUrl = "https://image.tmdb.org/t/p/original"
        const val ImdbUrl = "https://www.imdb.com/title/"
    }
}