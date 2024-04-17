package com.example.movielistapp.ui.screens.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.movielistapp.R
import com.example.movielistapp.data.model.Movie
import com.example.movielistapp.data.network.MovieApiService.Companion.ImdbUrl
import com.example.movielistapp.ui.components.StarRating


@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    navController: NavHostController
) {
    when(val uiState = viewModel.uiState.collectAsState().value) {
        is DetailState.Success -> DetailContent(
            movie = uiState.movie,
            onNavigateUp = { navController.navigateUp() },
            canBookmark = uiState.movie.id !in viewModel.bookmarkedMovies.collectAsState().value,
            onBookmark = { viewModel.bookmarkMovie(uiState.movie) },
            onDelete = { viewModel.deleteBookmarkedMovie(uiState.movie) }
        )
        is DetailState.Loading -> DetailLoading()
        is DetailState.Error -> DetailError()
    }


}

@Composable
fun DetailLoading() {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        CircularProgressIndicator()
    }
}
@Composable
fun DetailError() {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Internet connection problem!")
    }
}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    movie: Movie,
    canBookmark: Boolean,
    onBookmark: () -> Unit,
    onDelete: () -> Unit,
    onNavigateUp: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        val gradColor = listOf(
            Color.Transparent,
            //  Color.Transparent,
            MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
            MaterialTheme.colorScheme.background
        )
        Box(
            modifier = Modifier
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(360.dp)
                    .drawWithCache {
                        val bb = Brush.linearGradient(
                            colors = gradColor,
                            start = Offset(0.0f,250.0f),
                            end = Offset(0.0f,940.0f)
                        )
                        onDrawWithContent {
                            drawContent()
                            drawRect(bb)
                        }
                    },
                model = movie.backdropPath,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                onSuccess = { /*isLoading = true*/ }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                IconButton(
                    onClick = onNavigateUp
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(
                    onClick = if(canBookmark) onBookmark else onDelete,
                ) {
                    Icon(
                        imageVector = if(canBookmark) Icons.Default.BookmarkBorder else Icons.Default.Bookmark,
                        contentDescription = null,
                        tint = Color.Yellow
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(Alignment.BottomStart),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        modifier = Modifier ,
                        text = movie.title ,
                        style = MaterialTheme.typography.titleMedium ,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    StarRating(rating = movie.voteAverage)
                }
                Image(
                    modifier = Modifier
                        .size(32.dp),
                    painter = painterResource(id = R.drawable.under_18) ,
                    contentDescription = null,
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(top = 8.dp) ,
                    text = "Duration: ${movie.runtime}min    Status: ${movie.status}" ,
                    style = MaterialTheme.typography.bodySmall ,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                modifier = Modifier.padding(top = 16.dp) ,
                text = "Overview" ,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(top = 12.dp) ,
                text = movie.overview,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (movie.imdbId != null){
                Image(
                    modifier = Modifier
                        .clickable { openInBrowser(context,movie.imdbId) }
                        .size(58.dp),
                    painter = painterResource(id = R.drawable.imdb_icon),
                    contentDescription = null
                )
            }
        }
    }
}

private fun openInBrowser(ctx: Context, imbdId: String) {
    val url = Uri.parse(ImdbUrl + imbdId)
    val intent = Intent(Intent.ACTION_VIEW, url)
    ctx.startActivity(intent)

}
