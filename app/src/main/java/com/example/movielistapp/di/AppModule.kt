package com.example.movielistapp.di

import androidx.lifecycle.SavedStateHandle
import com.example.movielistapp.data.MovieRepository
import com.example.movielistapp.data.MovieRepositoryImpl
import com.example.movielistapp.data.network.MovieApiService
import com.example.movielistapp.data.network.RemoteDataSource
import com.example.movielistapp.data.network.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideRemoteDataSource(apiService: MovieApiService): RemoteDataSource {
        return RemoteDataSourceImpl(apiService)
    }

    @Provides
    fun provideMovieRepository(remoteDataSource: RemoteDataSource): MovieRepository {
        return MovieRepositoryImpl(remoteDataSource)
    }
}