package com.example.movielistapp.data.network.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val page: Int ,
    val results: List<Movie> ,
)

data class Movie(
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
    val runtime: Int,
    val status: String? ,
    @SerializedName("vote_average")
    val voteAverage: Double ,
    @SerializedName("vote_count")
    val voteCount: Int
)

@Entity("favorites")
data class MovieDto(
    val adult: Boolean ,
    val backdropPath: String ,
    @PrimaryKey(false)
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

fun MovieDto.toMovie(): Movie = Movie(
    adult, backdropPath, id, imdbId, title, overview, posterPath, releaseDate, runtime, status, voteAverage, voteCount
)

fun Movie.toMovieDto(): MovieDto = MovieDto(
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