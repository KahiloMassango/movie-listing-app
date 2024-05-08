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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.IOException

@HiltWorker
class SyncDatabaseWorker @AssistedInject constructor(
    @Assisted private val movieRepository: MovieRepository,
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
): CoroutineWorker(ctx, params) {

        override suspend fun doWork(): Result {

            withContext(Dispatchers.IO) {
                try {
                    movieRepository.saveLocalMovies(
                        async { movieRepository.fetchRemoteNowPlayingMovies() }.await().map {
                            it.asEntity(MovieCategory.NOW_PLAYING.name)
                        }
                    )
                    movieRepository.saveLocalMovies(
                        async { movieRepository.fetchRemotePopularMovies() }.await().map {
                            it.asEntity(MovieCategory.POPULAR.name)
                        }
                    )
                    movieRepository.saveLocalMovies(
                        async { movieRepository.fetchRemoteUpcomingMovies() }.await().map {
                            it.asEntity(MovieCategory.UPCOMING.name)
                        }
                    )
                } catch (e: IOException) {
                    Log.d("FetchRemoteMoviesWorker", e.toString())
                    return@withContext Result.retry()

                } catch (e: Exception) {
                    Log.d("FetchRemoteMoviesWorker", e.toString())
                    return@withContext Result.failure()
                }
            }
            return Result.success()
        }

}