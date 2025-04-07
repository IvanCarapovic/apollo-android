package dev.chapz.apollo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material.icons.twotone.Settings
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
        icon = Icons.TwoTone.Home,
        route = HomeDestination
    ),
    BottomNavigationItem(
        title = "Search",
        icon = Icons.TwoTone.Search,
        route = SearchDestination
    ),
    BottomNavigationItem(
        title = "Settings",
        icon = Icons.TwoTone.Settings,
        route = SettingsDestination
    )
)