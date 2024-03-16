package com.example.movielistapp.data.network.models

import com.example.movielistapp.data.local.entities.MovieEntity
import com.google.gson.annotations.SerializedName


data class NetworkMovieResponse(
    val page: Int ,
    val results: List<NetworkMovie> ,
)

data class NetworkMovie(
    val adult: Boolean ,
    @SerializedName("backdrop_path")
    val backdropPath: String ,
    val id: Int ,
    @SerializedName("imdb_id")
    val imdbId: String? ,
    @SerializedName("original_title")
    val title: String ,
    val overview: String ,
    @SerializedName("poster_path")
    val posterPath: String ,
    @SerializedName("release_date")
    val releaseDate: String ,
    val runtime: Int ,
    val status: String? ,
    val video: Boolean ,
    @SerializedName("vote_average")
    val voteAverage: Double ,
    @SerializedName("vote_count")
    val voteCount: Int
)

fun NetworkMovie.asEntity() = MovieEntity(
    adult ,
    backdropPath ,
    id ,
    imdbId ,
    title ,
    overview ,
    posterPath ,
    releaseDate ,
    runtime ,
    status ,
    voteAverage ,
    voteCount
)
