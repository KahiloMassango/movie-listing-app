package com.example.movielistapp.data.network.models

import com.example.movielistapp.data.model.Genre
import com.google.gson.annotations.SerializedName

data class NetworkMovie(
    val adult: Boolean ,
    @SerializedName("backdrop_path")
    val backdropPath: String ,
    val genres: List<Genre>? ,
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
