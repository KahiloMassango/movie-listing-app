package com.example.movielistapp.presentation.util.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movielistapp.domain.model.Movie
import com.example.movielistapp.ui.theme.MovieListAppTheme

@Composable
fun NowPlayingMovieList(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit
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

    }
}