package com.example.movielistapp.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movielistapp.data.model.Movie
import com.example.movielistapp.ui.components.ExtendedMovieCard
import com.example.movielistapp.ui.components.NowPlayingMovieList
import com.example.movielistapp.ui.components.StickyHeader
import com.example.movielistapp.ui.components.UpcomingMovieList
import com.example.movielistapp.ui.navigateToDetail
import com.example.movielistapp.ui.theme.MovieListAppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,

) {
    when(val uiState = viewModel.uiState.collectAsState().value){
        is HomeUiState.Loading -> LoadingScreen()
        is HomeUiState.Error -> ErrorScreen(
            retry = viewModel::refreshMovies
        )
        is HomeUiState.Success -> HomeContent(
             modifier = modifier,
             upcomingMovies = uiState.upcomingMovies,
             popularMovies = uiState.popularMovies,
             playingNowMovies = uiState.playingNowMovies,
             onMovieClick = { movie ->
                 navController.navigateToDetail(movie.id)
             }
         )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    upcomingMovies: List<Movie>,
    popularMovies: List<Movie>,
    playingNowMovies: List<Movie>,
    onMovieClick: (Movie) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        stickyHeader { StickyHeader(title = "Upcoming") }
        item {
            UpcomingMovieList(
                movies = upcomingMovies,
                onMovieClick = { movie ->
                    onMovieClick(movie)
                }
            )
        }
        stickyHeader { StickyHeader(title = "Now Playing") }

        item {
            NowPlayingMovieList(
                movies = popularMovies,
                onMovieClick = { movie ->
                    onMovieClick(movie)
                }
            )
        }

        stickyHeader { StickyHeader(title = "Popular") }
        items(playingNowMovies, { movie -> movie.id }){ movie ->
            ExtendedMovieCard(
                modifier = Modifier.padding(bottom = 16.dp),
                movie = movie,
                onClick = {
                    onMovieClick(movie)
                }
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        CircularProgressIndicator()
    }
}
@Composable
fun ErrorScreen(
    retry: () -> Unit
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Check your internet connection!")
        Button(onClick = retry ) {
            Text(text = "Try again")
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