package com.example.movielistapp.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movielistapp.data.network.model.MovieDto
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieDto: MovieDto)

    @Delete
    suspend fun deleteMovie(movieDto: MovieDto)

    @Query("SELECT * FROM favorites")
    fun getAllMovies(): Flow<List<MovieDto>>
}