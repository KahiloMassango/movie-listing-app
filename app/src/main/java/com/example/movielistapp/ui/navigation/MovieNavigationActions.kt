package com.example.movielistapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

object MovieRoute {
    const val HOME = "Home"
    const val SEARCH = "Search"
    const val FAVORITES = "Favorites"
    const val MAINCONTENT = "MainAppContent"
    const val DETAIL = "Detail"
}

data class MovieTopLevelDestination(
    val route: String,
    val icon: ImageVector
)



val TOP_DESTINATIONS = listOf(
    MovieTopLevelDestination(
    route = MovieRoute.HOME,
    icon = Icons.Default.Home
    ),
    MovieTopLevelDestination(
        route = MovieRoute.SEARCH,
        icon = Icons.Default.Search
    ),
    MovieTopLevelDestination(
        route = MovieRoute.FAVORITES,
        icon = Icons.Default.Bookmarks
    ),
)