package com.example.movielistapp.di

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.movielistapp.data.workers.SyncDatabaseWorker
import com.example.movielistapp.domain.repository.MovieRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MovieApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory : FetchRemoteMoviesWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

}

class FetchRemoteMoviesWorkerFactory @Inject constructor(private val movieRepository: MovieRepository): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = SyncDatabaseWorker(movieRepository, appContext, workerParameters)

}