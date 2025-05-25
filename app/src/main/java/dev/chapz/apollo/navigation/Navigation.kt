package dev.chapz.apollo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.chapz.apollo.ui.albums.AlbumList
import dev.chapz.apollo.ui.artists.ArtistsList
import dev.chapz.apollo.ui.genres.GenreList
import dev.chapz.apollo.ui.settings.SettingsScreen
import dev.chapz.apollo.ui.songs.SongList
import kotlinx.serialization.Serializable

@Serializable
object SongsNav

@Serializable
object AlbumsNav

@Serializable
object ArtistsNav

@Serializable
object GenreNav

@Serializable
object SettingsNav

@Serializable
object SongsDestination

@Serializable
object AlbumsDestination

@Serializable
object ArtistsDestination

@Serializable
object GenreDestination

@Serializable
object SettingsDestination

@Composable
fun NavigationWrapper(
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = SongsNav) {
        navigation<SongsNav>(startDestination = SongsDestination) {
            composable<SongsDestination> {
                SongList(paddingValues)
            }
        }

        navigation<AlbumsNav>(startDestination = AlbumsDestination) {
            composable<AlbumsDestination> {
                AlbumList(paddingValues)
            }
        }

        navigation<ArtistsNav>(startDestination = ArtistsDestination) {
            composable<ArtistsDestination> {
                ArtistsList(paddingValues)
            }
        }

        navigation<GenreNav>(startDestination = GenreDestination) {
            composable<GenreDestination> {
                GenreList(paddingValues)
            }
        }

        navigation<SettingsNav>(startDestination = SettingsDestination) {
            composable<SettingsDestination> {
                SettingsScreen(paddingValues)
            }
        }
    }
}