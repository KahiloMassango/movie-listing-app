package com.example.movielistapp.domain

import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.movielistapp.data.local.entities.MovieEntity
import com.example.movielistapp.data.repository.MovieRepository
import com.example.movielistapp.data.repository.workManager.BACKDROP_IMAGE_FILENAME_KEY
import com.example.movielistapp.data.repository.workManager.BACKDROP_IMAGE_URL_KEY
import com.example.movielistapp.data.repository.workManager.BACKDROP_OUTPUT
import com.example.movielistapp.data.repository.workManager.POSTER_IMAGE_FILENAME_KEY
import com.example.movielistapp.data.repository.workManager.POSTER_IMAGE_URL_KEY
import com.example.movielistapp.data.repository.workManager.POSTER_OUTPUT
import com.example.movielistapp.data.repository.workManager.workers.DownloadImageWorker

class BookmarkMovieUseCase(
    private val workManager: WorkManager,
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(movieEntity: MovieEntity) {
        val data = Data.Builder()
            .putString(BACKDROP_IMAGE_URL_KEY, movieEntity.backdropPath)
            .putString(POSTER_IMAGE_URL_KEY , movieEntity.posterPath)
            .putString(BACKDROP_IMAGE_FILENAME_KEY, movieEntity.id.toString() + "backdrop")
            .putString(POSTER_IMAGE_FILENAME_KEY , movieEntity.id.toString() + "poster")
            .build()
        // Trigger WorkManager to download image
        val downloadRequest = OneTimeWorkRequestBuilder<DownloadImageWorker>()
            .setInputData(data)
            .build()
        workManager.enqueue(downloadRequest)

        // Observe WorkManager result
        workManager.getWorkInfoByIdFlow(downloadRequest.id).collect { workInfo ->
            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                val backdropPath = workInfo.outputData.getString(BACKDROP_OUTPUT) !!
                val posterPath = workInfo.outputData.getString(POSTER_OUTPUT) !!
                movieRepository.saveLocalMovie(movieEntity.copy(backdropPath = backdropPath , posterPath = posterPath))
            }
        }
    }
}