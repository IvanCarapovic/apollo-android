package dev.chapz.apollo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: Any
)

// Define the list of navigation items
val bottomNavigationItems = listOf(
    BottomNavigationItem(
        title = "Home",
        icon = Icons.Filled.Home,
        route = HomeDestination
    ),
    BottomNavigationItem(
        title = "Search",
        icon = Icons.Filled.Search,
        route = SearchDestination
    ),
    BottomNavigationItem(
        title = "Settings",
        icon = Icons.Filled.Settings,
        route = SettingsDestination
    )
)