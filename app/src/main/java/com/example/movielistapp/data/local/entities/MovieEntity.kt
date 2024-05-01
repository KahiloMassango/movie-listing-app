package com.example.movielistapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movielistapp.domain.model.Movie

@Entity(tableName = "movies")
data class  MovieEntity(
    val adult: Boolean,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,
    @PrimaryKey(false)
    val id: Int,
    val imdbId: String?,
    val title: String,
    val overview: String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "release_sate")
    val releaseDate: String,
    val runtime: Int,
    val status: String?,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int,
    val category: String
)

enum class MovieCategory {
    POPULAR, UPCOMING, NOW_PLAYING
}

fun MovieEntity.asDomain() = Movie(
    adult,
    backdropPath ,
    id,
    imdbId,
    title,
    overview ,
    posterPath,
    releaseDate ,
    runtime ,
    status,
    voteAverage,
    voteCount
)