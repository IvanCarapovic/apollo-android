package dev.chapz.apollo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.chapz.apollo.navigation.BottomNavigationBar
import dev.chapz.apollo.navigation.NavigationWrapper
import dev.chapz.apollo.ui.theme.ApolloTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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