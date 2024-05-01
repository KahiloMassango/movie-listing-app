package com.example.movielistapp.data.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movielistapp.data.local.entities.MovieCategory
import com.example.movielistapp.domain.model.asEntity
import com.example.movielistapp.domain.repository.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.IOException

@HiltWorker
class SyncDatabaseWorker @AssistedInject constructor(
    @Assisted private val movieRepository: MovieRepository,
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
): CoroutineWorker(ctx, params) {

        override suspend fun doWork(): Result {
            try {
                movieRepository.saveLocalMovies(
                    movieRepository.fetchRemoteNowPlayingMovies().map {
                        it.asEntity(MovieCategory.NOW_PLAYING.name)
                    }
                )
                movieRepository.saveLocalMovies(
                    movieRepository.fetchRemotePopularMovies().map {
                        it.asEntity(MovieCategory.POPULAR.name)
                    }
                )
                movieRepository.saveLocalMovies(
                    movieRepository.fetchRemoteUpcomingMovies().map {
                        it.asEntity(MovieCategory.UPCOMING.name)
                    }
                )
                return Result.success()

            } catch (e: IOException) {
                Log.d("FetchRemoteMoviesWorker", e.toString())
                return Result.retry()

            } catch (e: Exception) {
                Log.d("FetchRemoteMoviesWorker", e.toString())
                return Result.failure()
            }
    }

}