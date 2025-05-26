package dev.chapz.apollo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.chapz.apollo.navigation.BottomNavigationBar
import dev.chapz.apollo.navigation.NavigationWrapper
import dev.chapz.apollo.player.MediaPlayerService
import dev.chapz.apollo.ui.theme.ApolloTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setup media service and controller
        startService(Intent(this, MediaPlayerService::class.java))
        viewModel.initMediaController()

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ApolloTheme(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = true,
                content = {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {

                        },
                        bottomBar = {
                            BottomNavigationBar(navController)
                        },
                        content = { paddingValues ->
                            NavigationWrapper(paddingValues, navController)
                        }
                    )
                }
            )
        }
    }
}