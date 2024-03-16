package com.example.movielistapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.movielistapp.data.model.Genre
import com.example.movielistapp.data.model.Movie
import com.example.movielistapp.data.model.movieOne
import com.example.movielistapp.ui.theme.MovieListAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultMovieCard(
    modifier: Modifier = Modifier ,
    movie: Movie ,
    onClick: () -> Unit
){
    val imageRequest  = rememberAsyncImagePainter(
        model = movie.posterPath
    )
    Card(
        modifier = modifier
            .width(180.dp),
           // .height(250.dp),
        onClick = { onClick() },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(0.dp, Color.Transparent)
    ) {
        Column(
            modifier = Modifier
        ) {
            AsyncImage(
                modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.posterPath)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
            )
            Spacer(Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = movie.title,
                style = MaterialTheme.typography.titleMedium
            )
            StarRating(
                modifier = Modifier.padding(bottom = 5.dp),
                rating = movie.voteAverage
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtendedMovieCard(
    modifier: Modifier = Modifier ,
    movie: Movie ,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = modifier
            .height(273.dp) ,
        onClick = { onClick() },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        border = BorderStroke(0.dp, Color.Transparent),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            AsyncImage(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.posterPath)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge
                )
                StarRating(
                    modifier = Modifier.padding(bottom = 8.dp),
                    rating = movie.voteAverage
                )

                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedMovieCard(
    modifier: Modifier = Modifier ,
    movie: Movie ,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        onClick = { onClick() },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        border = BorderStroke(0.dp, Color.Transparent),

    ) {
        Column(
            modifier = Modifier
        ) {
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .height(200.dp),
                model = movie.backdropPath,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1
                )
                StarRating(
                    modifier = Modifier,
                    rating = movie.voteAverage
                )
            }
        }
    }
}



@Composable
fun GenreList(modifier: Modifier = Modifier, genres: List<Genre>?) {
    Row(
        modifier = modifier
    ) {
        genres?.forEachIndexed { index, genre ->
            when (index) {
                0 -> Text(text = genre.name, style = MaterialTheme.typography.bodyMedium)
                else -> Text(text = ", ${genre.name}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
@Preview
private fun CardsPreview() {
    MovieListAppTheme {
        DefaultMovieCard(movie = movieOne , onClick = {})
    }
}