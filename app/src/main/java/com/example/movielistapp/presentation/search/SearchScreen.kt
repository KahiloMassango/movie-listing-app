package com.example.movielistapp.presentation.search

import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movielistapp.presentation.navigateToDetail
import com.example.movielistapp.presentation.util.components.ExtendedMovieCard
import com.example.movielistapp.ui.theme.MovieListAppTheme

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState().value
    SearchContent (
        modifier = modifier,
        searchUiState = uiState,
        onSearch = { searchText -> viewModel.search(searchText) },
        onMovieClick = { movieId -> navController.navigateToDetail(movieId) }
    )
}


@Composable
fun SearchContent(
    modifier: Modifier = Modifier,
    searchUiState: SearchUiState,
    onSearch: (String) -> Unit,
    onMovieClick: (Int) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var searchText by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        TextField(
            modifier = Modifier
                .clipScrollableContainer(Orientation.Vertical)
                .padding(bottom = 24.dp)
                .fillMaxWidth() ,
            value = searchText ,
            singleLine = true ,
            onValueChange = { searchText = it } ,
            shape = RoundedCornerShape(16.dp) ,
            placeholder = { Text(text = "Search") } ,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search ,
                    contentDescription = null
                )
            } ,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search) ,
            keyboardActions = KeyboardActions(onSearch = {
                focusManager.clearFocus()
                onSearch(searchText)
            }) ,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        if(searchUiState.movies.isEmpty() && !searchUiState.hasError) {
            DetailScreenMessage(message = "Start searching!")
        } 
        if(searchUiState.hasError) {
            DetailScreenMessage(message = "It seems that you're offline 🙂")
        } else {
            LazyColumn(
                modifier = Modifier,
            ) {
                items(searchUiState.movies) { movie ->
                    ExtendedMovieCard(
                        modifier = Modifier.padding(vertical = 8.dp) ,
                        movie = movie,
                        onClick = {
                            onMovieClick(movie.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DetailScreenMessage(
    message: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement =  Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
@Preview
fun SearchScreenPreview() {
    MovieListAppTheme {

    }
}

