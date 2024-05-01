package com.example.movielistapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movielistapp.data.local.dao.MovieDao
import com.example.movielistapp.data.local.entities.MovieEntity

@Database([MovieEntity::class], version = 10, exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun moviesDao(): MovieDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "movie_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}