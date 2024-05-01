package com.example.movielistapp.di

import android.content.Context
import com.example.movielistapp.data.local.AppDatabase
import com.example.movielistapp.data.local.MovieLocalDataSourceImpl
import com.example.movielistapp.data.remote.MovieApiService
import com.example.movielistapp.data.remote.MovieRemoteDataSourceImpl
import com.example.movielistapp.data.repository.MovieRepositoryImpl
import com.example.movielistapp.data.repository.WorkManagerRepositoryImpl
import com.example.movielistapp.domain.datasource.MovieLocalDataSource
import com.example.movielistapp.domain.datasource.MovieRemoteDataSource
import com.example.movielistapp.domain.repository.MovieRepository
import com.example.movielistapp.domain.repository.WorkManagerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


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

    @Singleton
    @Provides
    fun provideWorkManagerRepository(@ApplicationContext ctx: Context): WorkManagerRepository {
        return WorkManagerRepositoryImpl(ctx)
    }


    @Provides
    fun provideRemoteDataSource(apiService: MovieApiService): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(apiService)
    }


    @Provides
    fun provideLocalDataSource(@ApplicationContext context: Context): MovieLocalDataSource {
        return MovieLocalDataSourceImpl(
            AppDatabase.getDatabase(context).moviesDao()
        )
    }

    @Provides
    fun provideMovieRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(movieRemoteDataSource, movieLocalDataSource)
    }

}