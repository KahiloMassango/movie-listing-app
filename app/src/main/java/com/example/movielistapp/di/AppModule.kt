package com.example.movielistapp.di

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.work.WorkManager
import com.example.movielistapp.data.local.MovieLocalDataSource
import com.example.movielistapp.data.local.MovieLocalDataSourceImpl
import com.example.movielistapp.data.local.dao.AppDatabase
import com.example.movielistapp.data.local.dao.MovieDao
import com.example.movielistapp.data.network.MovieApiService
import com.example.movielistapp.data.network.MovieRemoteDataSource
import com.example.movielistapp.data.network.MovieRemoteDataSourceImpl
import com.example.movielistapp.data.repository.MovieRepository
import com.example.movielistapp.data.repository.MovieRepositoryImpl
import com.example.movielistapp.domain.BookmarkMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

interface CustomHandler {
    val savedStateHandle: SavedStateHandle
}

class HandlerImpl(handler: SavedStateHandle): CustomHandler {
    override val savedStateHandle = handler
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApiService(): MovieApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSavedStateHandle(): CustomHandler {
        return HandlerImpl(SavedStateHandle())
    }

    @Provides
    fun provideRemoteDataSource(apiService: MovieApiService): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(apiService)
    }

    @Provides
    fun provideDatabaseService(@ApplicationContext context: Context): MovieDao {
        return AppDatabase.getDatabase(context).movieDao()
    }

    @Provides
    fun provideLocalDataSource(movieDao: MovieDao): MovieLocalDataSource {
        return MovieLocalDataSourceImpl(movieDao)
    }

    @Provides
    fun provideMovieRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(movieRemoteDataSource, movieLocalDataSource)
    }

    @Provides
    fun provideWorkManager(@ApplicationContext ctx: Context): WorkManager {
        return WorkManager.getInstance(ctx)
    }

    @Provides
    fun provideBookmarkMovieUseCase(workManager: WorkManager, movieRepository: MovieRepository): BookmarkMovieUseCase {
        return BookmarkMovieUseCase(workManager, movieRepository)
    }

}