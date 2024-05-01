package com.example.movielistapp.presentation

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movielistapp.presentation.detail.DetailScreen
import com.example.movielistapp.presentation.util.navigation.MovieRoute


@Composable
fun MovieApp(
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        modifier = Modifier
            .statusBarsPadding(),
        navController = navController,
        startDestination = MovieRoute.MAINCONTENT
    ) {
        composable(MovieRoute.MAINCONTENT) {
            MainAppContent(navController = navController)
        }
        composable("${MovieRoute.DETAIL}/{movieId}") {
            DetailScreen(navController = navController)
        }

    }
}


fun NavHostController.navigateToDetail(movieId: Int) {
    // Pop up to the start destination of the graph to
    // avoid building up a large stack of destinations
    // on the back stack as users select items
    this.navigate("${MovieRoute.DETAIL}/$movieId"){
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


