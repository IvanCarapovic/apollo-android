package dev.chapz.apollo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import dev.chapz.apollo.ui.songs.SongList
import dev.chapz.apollo.ui.theme.ApolloTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

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
                        content = { paddingValues ->
                            Box {
                                SongList(paddingValues)
                                FloatingPlaybackControls(paddingValues)
                                TopShadow(paddingValues)
                            }
                        }
                    )
                }
            )
        }
    }

    @Composable
    fun TopShadow(paddingValues: PaddingValues) {
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(paddingValues.calculateTopPadding())
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            Color.Transparent
                        )
                    )
                )
        )
    }

    @Composable
    fun FloatingPlaybackControls(paddingValues: PaddingValues) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = paddingValues.calculateBottomPadding() + 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.size(64.dp),
                    onClick = {

                    },
                    content = {
                        Icon(
                            imageVector = Icons.Rounded.SkipPrevious,
                            contentDescription = "Previous",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    shapes = IconButtonDefaults.shapes().copy(
                        shape = MaterialShapes.Square.toShape(),
                        pressedShape = MaterialShapes.Circle.toShape()
                    ),
                    colors = IconButtonDefaults.iconButtonColors().copy(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        disabledContentColor = MaterialTheme.colorScheme.inverseSurface,
                        disabledContainerColor = MaterialTheme.colorScheme.inversePrimary
                    )
                )
                IconButton(
                    modifier = Modifier.size(96.dp),
                    onClick = {

                    },
                    content = {
                        Icon(
                            imageVector = Icons.Rounded.PlayArrow,
                            contentDescription = "Pause",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    shapes = IconButtonDefaults.shapes().copy(
                        shape = MaterialShapes.Cookie6Sided.toShape(),
                        pressedShape = MaterialShapes.Cookie9Sided.toShape()
                    ),
                    colors = IconButtonDefaults.iconButtonColors().copy(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        disabledContentColor = MaterialTheme.colorScheme.onTertiary,
                        disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                )
                IconButton(
                    modifier = Modifier.size(64.dp),
                    onClick = {

                    },
                    content = {
                        Icon(
                            imageVector = Icons.Rounded.SkipNext,
                            contentDescription = "Next",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    shapes = IconButtonDefaults.shapes().copy(
                        shape = MaterialShapes.Square.toShape(),
                        pressedShape = MaterialShapes.Circle.toShape()
                    ),
                    colors = IconButtonDefaults.iconButtonColors().copy(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContentColor = MaterialTheme.colorScheme.onTertiary,
                        disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                )
            }
        }

    }


}