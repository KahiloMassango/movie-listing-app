package com.example.movielistapp.data.workers

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import coil.Coil
import coil.request.ImageRequest
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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