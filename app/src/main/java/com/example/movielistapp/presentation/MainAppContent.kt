package com.example.movielistapp.presentation

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
import com.example.movielistapp.presentation.home.HomeScreen
import com.example.movielistapp.presentation.search.SearchScreen
import com.example.movielistapp.presentation.util.components.MovieTopBar
import com.example.movielistapp.presentation.util.navigation.MovieBottomNavigationBar
import com.example.movielistapp.presentation.util.navigation.MovieRoute

@Composable
fun MainAppContent(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var currentScreen by rememberSaveable { mutableStateOf(MovieRoute.HOME) }
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
            }
        }
    }
}