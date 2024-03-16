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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.movielistapp.data.network.MovieApiService.Companion.ImdbUrl
import com.example.movielistapp.ui.components.StarRating


@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current

    Column(
        modifier = modifier
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
                            colors = gradColor ,
                            start = Offset(0.0f , 250.0f) ,
                            end = Offset(0.0f , 940.0f)
                        )
                        onDrawWithContent {
                            drawContent()
                            drawRect(bb)
                        }
                    },
                model = uiState.backdropPath,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            IconButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = { viewModel.saveMovie(movie = uiState) }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Bookmark ,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(
                onClick = { navController.navigateUp() }
            ) {
               Icon(
                   imageVector = Icons.Default.ArrowBackIosNew ,
                   contentDescription = null,
                   tint = MaterialTheme.colorScheme.onBackground
               )
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
                        text = uiState.title ,
                        style = MaterialTheme.typography.headlineLarge ,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    StarRating(rating = uiState.voteAverage)
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
                    text = "Duration: ${uiState.runtime}min    Status: ${uiState.status}" ,
                    style = MaterialTheme.typography.bodySmall ,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                modifier = Modifier.padding(top = 16.dp) ,
                text = "Overview" ,
                style = MaterialTheme.typography.bodyLarge ,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(top = 12.dp) ,
                text = uiState.overview,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Text(
            modifier = Modifier.padding(start = 12.dp, top = 16.dp),
            text = "See on: ",
            style = MaterialTheme.typography.labelLarge
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (uiState.imdbId != null){
                Image(
                    modifier = Modifier
                        .clickable { openInBrowser(context , uiState.imdbId) }
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
