package com.example.movielistapp.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.movielistapp.ui.components.MovieTopBar
import com.example.movielistapp.ui.navigation.MovieBottomNavigationBar
import com.example.movielistapp.ui.navigation.MovieRoute
import com.example.movielistapp.ui.screens.favorite.FavoriteScreen
import com.example.movielistapp.ui.screens.home.HomeScreen
import com.example.movielistapp.ui.screens.search.SearchScreen

@Composable
fun MainAppContent(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var currentScreen by rememberSaveable { mutableStateOf(MovieRoute.SEARCH) }
    Scaffold(
        topBar = {
            MovieTopBar(currentScreen)
        },
        bottomBar = {
            MovieBottomNavigationBar(
                selectedDestination = currentScreen,
                navigateToTopLevelDestination = { destination ->
                    currentScreen = destination.route
                }
            )
        }
    ) { paddingValues->
        AnimatedContent(
            modifier = modifier.padding(paddingValues),
            targetState = currentScreen , label = ""
        ) {screen ->
            when(screen) {
                MovieRoute.HOME -> HomeScreen(navController = navController)
                MovieRoute.SEARCH -> SearchScreen(navController = navController)
                MovieRoute.FAVORITES -> FavoriteScreen(navController = navController)
            }
        }
    }
}