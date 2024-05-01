package com.example.movielistapp.domain.model


import com.example.movielistapp.data.local.entities.MovieEntity

data class Movie(
    val adult: Boolean ,
    val backdropPath: String ,
    val id: Int ,
    val imdbId: String?,
    val title: String ,
    val overview: String ,
    val posterPath: String ,
    val releaseDate: String ,
    val runtime: Int,
    val status: String? ,
    val voteAverage: Double ,
    val voteCount: Int
)

fun Movie.asEntity(category: String): MovieEntity = MovieEntity(
    adult,
    backdropPath,
    id,
    imdbId,
    title,
    overview,
    posterPath,
    releaseDate,
    runtime,
    status,
    voteAverage,
    voteCount,
    category = category
)
