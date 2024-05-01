package com.example.movielistapp.presentation.util.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
