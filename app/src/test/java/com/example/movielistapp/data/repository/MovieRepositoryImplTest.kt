package com.example.movielistapp.data.repository

import com.example.movielistapp.data.local.FakeLocalDataSource
import com.example.movielistapp.data.local.FakeRemoteDataSource
import com.example.movielistapp.data.local.entities.MovieCategory
import com.example.movielistapp.data.local.entities.asDomain
import com.example.movielistapp.playingMovieTest
import com.example.movielistapp.popularMovieTest
import com.example.movielistapp.upcomingMovieTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class MovieRepositoryImplTest {
    private val  repository = MovieRepositoryImpl(FakeRemoteDataSource(), FakeLocalDataSource())
    

    @Test
    fun testGetMovies_FromEmptyDatabase_returnsEmptyList() = runTest {
        val actual = repository.getLocalMovieByCategoryStream(MovieCategory.POPULAR.name).first()
        assert(actual.isEmpty())
    }

    @Test
    fun getMoviesByCategory_fromDatabase_returnsCorrectCategoryMovies() = runTest {
        // Given
        val movie = popularMovieTest
        repository.saveLocalMovies(listOf(movie))

        // When
        val result = repository.getLocalMovieByCategoryStream(MovieCategory.POPULAR.name).first()

        // Then
        Assert.assertEquals(listOf(movie.asDomain()), result)
    }

    @Test
    fun `get movie by id returns correct movie`() = runTest {
        // Given
        val movie = popularMovieTest
        repository.saveLocalMovies(listOf(movie))

        // When
        val result = repository.getLocalMovieByID(movie.id)

        // Then
        Assert.assertNotNull(result)
    }

    @Test
    fun `get movie by id returns null if not found`() = runTest {
        // Given
        val movieId = 999
        val movie = popularMovieTest
        repository.saveLocalMovies(listOf(movie))

        // When
        val result = repository.getLocalMovieByID(movieId)

        // Then
        Assert.assertNull(result)
    }

    @Test
    fun testFetchRemotePopularMovies_verifyMovieList( ) = runTest {
        // When
        val result = repository.fetchRemotePopularMovies()

        // Then
        Assert.assertEquals(listOf(popularMovieTest.asDomain()), result)
    }

    @Test
    fun testFetchRemoteUpcomingMovies_verifyMovieList( ) = runTest {
        // When
        val result = repository.fetchRemoteUpcomingMovies()

        // Then
        Assert.assertEquals(listOf(upcomingMovieTest.asDomain()), result)
    }

    @Test
    fun testFetchRemoteNowPlayingMovies_verifyMovieList( ) = runTest {
        // When
        val result = repository.fetchRemoteNowPlayingMovies()

        // Then
        Assert.assertEquals(listOf(playingMovieTest.asDomain()), result)
    }

    @Test
    fun testFetchRemoteMovieById_verifyMovie( ) = runTest {
        // Given
        val movieId = popularMovieTest.id

        // When
        val result = repository.fetchRemoteMovieById(movieId)

        // Then
        Assert.assertEquals(popularMovieTest.asDomain(), result)

    }

    @Test
    fun testFetchMoviesByQuery_verifyMovieList() = runTest {
        // Given
        val query = "test"

        // When
        val result = repository.fetchMoviesByQuery(query)

        // Then
        val expect = listOf(
            popularMovieTest.asDomain(),
            upcomingMovieTest.asDomain(),
            playingMovieTest.asDomain()
        )
        Assert.assertEquals(expect, result)
    }

    @Test
    fun testFetchPopularMovies_verifyMovieList() = runTest {
        // When
        val result = repository.fetchRemotePopularMovies()

        // Then
        Assert.assertEquals(listOf(popularMovieTest.asDomain()), result)
    }
}