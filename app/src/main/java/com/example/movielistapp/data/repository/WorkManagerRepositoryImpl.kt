package com.example.movielistapp.data.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.movielistapp.data.workers.SyncDatabaseWorker
import com.example.movielistapp.domain.repository.WorkManagerRepository

class WorkManagerRepositoryImpl(ctx: Context) : WorkManagerRepository {

    private val workManager = WorkManager.getInstance(ctx)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun syncDatabase() {

        val syncWorker = OneTimeWorkRequestBuilder<SyncDatabaseWorker>()
            .build()

        workManager.enqueue(syncWorker)
    }
}