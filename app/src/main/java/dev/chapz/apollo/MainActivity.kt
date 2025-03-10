package dev.chapz.apollo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.chapz.apollo.navigation.BottomNavigationBar
import dev.chapz.apollo.navigation.NavigationWrapper
import dev.chapz.apollo.permissions.AudioPermissionRequestButton
import dev.chapz.apollo.permissions.NotificationPermissionRequestButton
import dev.chapz.apollo.ui.common.Header
import dev.chapz.apollo.ui.theme.ApolloTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            ApolloTheme(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false,
                content = {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            Header()
                        },
                        bottomBar = {
                            BottomNavigationBar(navController)
                        },
                        content = { paddingValues ->
                            Box(
                                modifier = Modifier.padding(paddingValues).fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    AudioPermissionRequestButton()
                                    NotificationPermissionRequestButton()
                                }
                            }
                            NavigationWrapper(paddingValues, navController)
                        }
                    )
                }
            )
        }
    }
}