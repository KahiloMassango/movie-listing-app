package com.example.movielistapp.data.network

import com.example.movielistapp.data.model.Movie
import com.example.movielistapp.data.network.models.NetworkMovieResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


const val apiKey = "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4OWQyN2E0NzZiNTBjM2NiNGIwMTE0M2U5NjA3MTc1ZSIsInN1YiI6IjY1ZTJmMGNjZmUwNzdhMDE4NTBlODcyYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.RTc2jLSQ-buyY4CQVaw3qgdLytQ3pVMJgS99SJvLvNI"

interface MovieApiService {
    @Headers(apiKey)
    @GET("movie/popular?language=en-US&page=1")
    suspend fun getPopularMovies(): NetworkMovieResponse

    @Headers(apiKey)
    @GET("movie/upcoming?language=en-US&page=1")
    suspend fun getUpcomingMovies(): NetworkMovieResponse

    @Headers(apiKey)
    @GET("movie/now_playing?language=en-US&page=1")
    suspend fun getNowPlayingMovies(): NetworkMovieResponse

    @Headers(apiKey)
    @GET("movie/{movieId}")
    suspend fun getMovieById(@Path("movieId") id: Int): Movie

    @Headers(apiKey)
    @GET("search/movie?include_adult=true")
    suspend fun searchMovieByQuery(@Query("query") query: String): NetworkMovieResponse

    companion object {
        const val ImageUrl = "https://image.tmdb.org/t/p/original"
        const val ImdbUrl = "https://www.imdb.com/title/"
    }
}