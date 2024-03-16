package com.example.movielistapp.data.model


data class MoviesResponse(
    val page: Int ,
    val results: List<Movie> ,
)

data class Movie(
    val adult: Boolean,
    val backdropPath: String,
    val id: Int,
    val imdbId: String?,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val runtime: Int,
    val status: String?,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
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
    video = false,
    voteAverage = 5.91,
    voteCount = 94
)
val movieTwo = movieOne.copy(id = 33)
val movieThree = movieOne.copy(id = 3)
val movieFour = movieOne.copy(id = 435)
val movieFive = movieOne.copy(id = 3354)


val movieList = listOf(movieOne, movieTwo, movieThree, movieFour, movieFive)