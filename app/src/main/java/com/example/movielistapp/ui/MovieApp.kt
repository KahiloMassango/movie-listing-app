package com.example.movielistapp.ui

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movielistapp.ui.navigation.MovieRoute
import com.example.movielistapp.ui.navigation.MovieTopLevelDestination
import com.example.movielistapp.ui.screens.detail.DetailScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp(
    navController: NavHostController = rememberNavController()
) {
    val backStack by navController.currentBackStackEntryAsState()
    val currentScreen = backStack?.destination?.route ?: MovieRoute.HOME

    NavHost(
        modifier = Modifier
            .statusBarsPadding(),
        navController = navController,
        startDestination = MovieRoute.MAINCONTENT
    ) {
        composable(MovieRoute.MAINCONTENT) {
            MainAppContent(navController = navController)
        }
        composable(MovieRoute.DETAIL) {

            DetailScreen(navController = navController)
        }
    }
}

fun NavHostController.navigateTo(destination: MovieTopLevelDestination) {
    // Pop up to the start destination of the graph to
    // avoid building up a large stack of destinations
    // on the back stack as users select items
    this.navigate(destination.route){
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}

fun NavHostController.navigateToDetail(movieId: Int) {
    navigate("Detail/$movieId")
}

