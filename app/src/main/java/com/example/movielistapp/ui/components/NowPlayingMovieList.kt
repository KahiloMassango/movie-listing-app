package com.example.movielistapp.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movielistapp.data.model.NetworkMovie
import com.example.movielistapp.data.model.movieList
import com.example.movielistapp.ui.theme.MovieListAppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NowPlayingMovieList(
    modifier: Modifier = Modifier ,
    movies: List<NetworkMovie> ,
    onMovieClick: (NetworkMovie) -> Unit
) {
    val state = rememberLazyListState()
    LazyRow(
        modifier = modifier,
        state = state,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(movies, { movie -> movie.id }){ movie ->
            DefaultMovieCard(movie = movie, onClick = { onMovieClick(movie) })
        }
    }
}

@Composable
@Preview
private fun NowPlayingMoviePreview() {
    MovieListAppTheme {
        NowPlayingMovieList(
            movies = movieList,
            onMovieClick = { 44 }
        )
    }
}