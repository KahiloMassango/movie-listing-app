package com.example.movielistapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.movielistapp.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("Select * from movies WHERE category = :category")
    fun getMovieByCategory(category: String): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieEntity>)

    @Query("DELETE FROM movies WHERE id NOT IN (:movieIds)")
    suspend fun deleteMoviesNotInList(movieIds: List<Int>)

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity?

}


@Transaction
suspend fun MovieDao.replaceAll(movies: List<MovieEntity>) {
    insertAll(movies)
    val movieIds = movies.map { it.id }
    deleteMoviesNotInList(movieIds)
}
