package com.example.movielistapp.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.movielistapp.ui.theme.MovieListAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopBar(
    title: String
){
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
    )
}


@Composable
@Preview()
private fun ComponentsPreview() {
    MovieListAppTheme {
        //MovieDetailAppBar({})
    }
}
