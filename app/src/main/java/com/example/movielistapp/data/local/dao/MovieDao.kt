package com.example.movielistapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movielistapp.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Query("SELECT * FROM favorites WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Delete
    suspend fun deleteMovie(movieEntity: MovieEntity)

    @Query("SELECT * FROM favorites")
    fun getBookmarkedMoviesStream(): Flow<List<MovieEntity>>

    @Query("SELECT id FROM favorites")
    fun getBookmarkedMoviesIdsStream(): Flow<List<Int>>
}