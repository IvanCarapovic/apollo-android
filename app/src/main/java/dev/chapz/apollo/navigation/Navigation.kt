package dev.chapz.apollo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.chapz.apollo.ui.home.HomeScreen
import dev.chapz.apollo.ui.search.SearchScreen
import dev.chapz.apollo.ui.settings.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeNav

@Serializable
object SearchNav

@Serializable
object SettingsNav

@Serializable
object HomeDestination

@Serializable
object SearchDestination

@Serializable
object SettingsDestination

@Composable
fun NavigationWrapper(
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = HomeNav) {
        navigation<HomeNav>(startDestination = HomeDestination) {
            composable<HomeDestination> {
                HomeScreen(paddingValues)
            }
        }

        navigation<SearchNav>(startDestination = SearchDestination) {
            composable<SearchDestination> {
                SearchScreen(paddingValues)
            }
        }

        navigation<SettingsNav>(startDestination = SettingsDestination) {
            composable<SettingsDestination> {
                SettingsScreen(paddingValues)
            }
        }
    }
}