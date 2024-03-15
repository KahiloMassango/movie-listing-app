package com.example.movielistapp.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movielistapp.ui.components.ExtendedMovieCard
import com.example.movielistapp.ui.components.NowPlayingMovieList
import com.example.movielistapp.ui.components.StickyHeader
import com.example.movielistapp.ui.components.UpcomingMovieList
import com.example.movielistapp.ui.navigation.MovieRoute
import com.example.movielistapp.ui.theme.MovieListAppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,

) {
    val uiState = viewModel.uiState.collectAsState().value
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        stickyHeader { StickyHeader(title = "Upcoming") }

        item {
           UpcomingMovieList(
               movies = uiState.upcomingMovies,
               onMovieClick = { movie ->
                   viewModel.parseMovieToDetail(movie)
                   navController.navigate(MovieRoute.DETAIL)
               }
           )
       }

        stickyHeader { StickyHeader(title = "Now Playing") }

        item {
            NowPlayingMovieList(
                movies = uiState.playingNowMovies,
                onMovieClick = { movie ->
                    viewModel.parseMovieToDetail(movie)
                    navController.navigate(MovieRoute.DETAIL)
                }
            )
        }

        stickyHeader { StickyHeader(title = "Popular") }
        items(uiState.popularMovies, { movie -> movie.id }){ movie ->
            ExtendedMovieCard(
                modifier = Modifier.padding(bottom = 16.dp),
                movie = movie,
                onClick = {
                    viewModel.parseMovieToDetail(movie)
                    navController.navigate(MovieRoute.DETAIL)
                }
            )
        }
    }

}


@Composable
@Preview
private fun HomePreview() {
    MovieListAppTheme {
        HomeScreen( navController = rememberNavController())
    }
}