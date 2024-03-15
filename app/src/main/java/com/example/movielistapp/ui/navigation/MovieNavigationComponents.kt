package com.example.movielistapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.movielistapp.ui.theme.MovieListAppTheme

@Composable
fun MovieBottomNavigationBar(
    selectedDestination: String,
    navigateToTopLevelDestination: (MovieTopLevelDestination) -> Unit
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        TOP_DESTINATIONS.forEach { movieDestination ->
            NavigationBarItem(
                selected = selectedDestination == movieDestination.route,
                onClick = { navigateToTopLevelDestination(movieDestination) },
                icon = {
                    Icon(imageVector = movieDestination.icon,
                        contentDescription = movieDestination.route
                    )
                }
            )

        }
    }

}

@Composable
@Preview
private fun ComponentsPreview() {
    MovieListAppTheme {
        MovieBottomNavigationBar(
            TOP_DESTINATIONS[2].route,
            { }
        )
    }
}
