package dev.chapz.apollo.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.QueueMusic
import androidx.compose.material.icons.twotone.Album
import androidx.compose.material.icons.twotone.Audiotrack
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material.icons.twotone.Speaker
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: Any
)

// Define the list of navigation items
val bottomNavigationItems = listOf(
    BottomNavigationItem(
        title = "Songs",
        icon = Icons.TwoTone.Audiotrack,
        route = SongsDestination
    ),
    BottomNavigationItem(
        title = "Albums",
        icon = Icons.TwoTone.Album,
        route = AlbumsDestination
    ),
    BottomNavigationItem(
        title = "Artists",
        icon = Icons.TwoTone.Person,
        route = ArtistsDestination
    ),
    BottomNavigationItem(
        title = "Genres",
        icon = Icons.TwoTone.Speaker,
        route = GenreDestination,
    ),
    BottomNavigationItem(
        title = "Playlists",
        icon = Icons.AutoMirrored.TwoTone.QueueMusic,
        route = SettingsDestination
    )
)