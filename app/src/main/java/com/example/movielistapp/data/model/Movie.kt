package com.example.movielistapp.data.model


import com.example.movielistapp.data.local.entities.MovieEntity


data class Movie(
    val adult: Boolean ,
    val backdropPath: String ,
    val id: Int ,
    val imdbId: String? ,
    val title: String ,
    val overview: String ,
    val posterPath: String ,
    val releaseDate: String ,
    val runtime: Int,
    val status: String? ,
    val voteAverage: Double ,
    val voteCount: Int
)



fun Movie.asEntity(): MovieEntity = MovieEntity(
    adult, backdropPath, id, imdbId, title, overview, posterPath, releaseDate, runtime, status, voteAverage, voteCount
)



val movieOne = Movie(
    adult = false,
    backdropPath = "https://image.tmdb.org/t/p/original/4woSOUD0equAYzvwhWBHIJDCM88.jpg",
    id = 1096197,
    imdbId = "tt16253418",
    title = "No Way Up",
    overview = "Characters from different backgrounds are thrown together when the plane they're travelling on crashes into the Pacific Ocean. A nightmare fight for survival ensues with the air supply running out and dangers creeping in from all sides.",
    posterPath = "https://image.tmdb.org/t/p/original/7FpGJTN8IL6IBvQMp6YHBFyhO9Z.jpg",
    releaseDate = "2024-01-18",
    runtime = 90,
    status = "Released",
    voteAverage = 5.91,
    voteCount = 94
)
val movieTwo = movieOne.copy(id = 33)
val movieThree = movieOne.copy(id = 3)
val movieFour = movieOne.copy(id = 435)
val movieFive = movieOne.copy(id = 3354)


val movieList = listOf(movieOne , movieTwo , movieThree , movieFour , movieFive)