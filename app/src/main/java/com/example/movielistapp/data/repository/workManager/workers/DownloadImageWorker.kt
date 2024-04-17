package com.example.movielistapp.data.repository.workManager.workers

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import coil.Coil
import coil.request.ImageRequest
import com.example.movielistapp.data.repository.workManager.BACKDROP_IMAGE_FILENAME_KEY
import com.example.movielistapp.data.repository.workManager.BACKDROP_IMAGE_URL_KEY
import com.example.movielistapp.data.repository.workManager.BACKDROP_OUTPUT
import com.example.movielistapp.data.repository.workManager.POSTER_IMAGE_FILENAME_KEY
import com.example.movielistapp.data.repository.workManager.POSTER_IMAGE_URL_KEY
import com.example.movielistapp.data.repository.workManager.POSTER_OUTPUT
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DownloadImageWorker(
    ctx: Context,
    params: WorkerParameters
): CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        val backdropUrl = inputData.getString(BACKDROP_IMAGE_URL_KEY)!!
        val posterUrl = inputData.getString(POSTER_IMAGE_URL_KEY)!!
        val backdropFileName = inputData.getString(BACKDROP_IMAGE_FILENAME_KEY)!!
        val posterFileName = inputData.getString(POSTER_IMAGE_FILENAME_KEY)!!

        val backdrop = downloadBitmapFromUrl(applicationContext, backdropUrl)
        val poster = downloadBitmapFromUrl(applicationContext, posterUrl)

        val backdropPath = saveBitmapToInternalStorage(applicationContext, backdrop, backdropFileName)
        val posterPath = saveBitmapToInternalStorage(applicationContext, poster, posterFileName)

        val outputData = Data.Builder()
            .putString(BACKDROP_OUTPUT, backdropPath)
            .putString(POSTER_OUTPUT, posterPath)

        return Result.success(outputData.build())
    }

}

private suspend fun downloadBitmapFromUrl(ctx: Context, url: String): Bitmap {
    val request = ImageRequest.Builder(ctx  )
        .data(url)
        .allowHardware(false)
        .build()

    return Coil.imageLoader(ctx).execute(request).drawable!!.toBitmap()
}

private fun saveBitmapToInternalStorage(ctx: Context, bitmap: Bitmap, fileName: String): String {
    // Creating a directory within the app's private storage where the image will be saved
    val directory = ctx.getDir("images", Context.MODE_PRIVATE)

    // Generate a unique file name for the image
    val file = File(directory, "${fileName}.jpg")

    try {
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    // Return the absolute path of the saved image
    return file.absolutePath
}