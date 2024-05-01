package com.example.movielistapp

import com.example.movielistapp.data.local.entities.MovieCategory
import com.example.movielistapp.data.local.entities.MovieEntity
import com.example.movielistapp.domain.model.Movie

val upcomingMovieTest = MovieEntity(
    // Non-nullable fields
    id = 123,
    title = "The Test",
    overview = "test",
    posterPath = "path",
    backdropPath = "backdrop",
    releaseDate = "2022-03-04",
    voteAverage = 7.8,
    voteCount = 1,

    // Nullable fields
    adult = false,
    imdbId = "imdbID",
    runtime = 0,
    status = "Released",
    category = MovieCategory.UPCOMING.name
)

val playingMovieTest = MovieEntity(
    // Non-nullable fields
    id = 231,
    title = "The Test",
    overview = "test",
    posterPath = "path",
    backdropPath = "backdrop",
    releaseDate = "2022-03-04",
    voteAverage = 7.8,
    voteCount = 3,

    // Nullable fields
    adult = false,
    imdbId = "imdbID",
    runtime = 0,
    status = "Released",
    category = MovieCategory.NOW_PLAYING.name
)
val popularMovieTest = MovieEntity(
    // Non-nullable fields
    id = 321,
    title = "The Test",
    overview = "test",
    posterPath = "path",
    backdropPath = "backdrop",
    releaseDate = "2022-03-04",
    voteAverage = 7.8,
    voteCount = 3,

    // Nullable fields
    adult = false,
    imdbId = "imdbID",
    runtime = 0,
    status = "Released",
    category = MovieCategory.POPULAR.name
)

val movieTest = Movie(
    // Non-nullable fields
    id = 321,
    title = "The Test",
    overview = "test",
    posterPath = "path",
    backdropPath = "backdrop",
    releaseDate = "2022-03-04",
    voteAverage = 7.8,
    voteCount = 3,

    // Nullable fields
    adult = false,
    imdbId = "imdbID",
    runtime = 0,
    status = "Released",
)

val movieList = listOf(playingMovieTest, popularMovieTest, upcomingMovieTest)
val upcomingMoviesList = listOf(upcomingMovieTest)
val playingNiwMoviesList = listOf(playingMovieTest)
val popularMoviesList = listOf(popularMovieTest)