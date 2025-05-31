package dev.chapz.apollo.app

import android.media.session.PlaybackState
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chapz.apollo.permissions.AudioPermissionRequestButton
import dev.chapz.apollo.permissions.NotificationPermissionRequestButton
import dev.chapz.apollo.ui.songs.SongList
import dev.chapz.apollo.ui.theme.ApolloTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ApolloTheme {
                Scaffold(
                    content = { padding ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            content = {
                                val permissionsState = viewModel.permissionsGranted.collectAsState()

                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(padding),
                                    contentAlignment = Alignment.Center,
                                    content = {
                                        if (!permissionsState.value.first) {
                                            AudioPermissionRequestButton {
                                                viewModel.permissionsGranted.value =
                                                    Pair(true, viewModel.permissionsGranted.value.second)
                                            }
                                        }

                                        if (!permissionsState.value.second) {
                                            NotificationPermissionRequestButton {
                                                viewModel.permissionsGranted.value =
                                                    Pair(viewModel.permissionsGranted.value.first, true)
                                            }
                                        }
                                    }
                                )

                                if (permissionsState.value.first && permissionsState.value.second) {
                                    SongList(
                                        paddingValues = padding,
                                        viewModel = viewModel,
                                    )
                                    PlaybackControls(padding)
                                }

                            }
                        )
                    }
                )
            }
        }
    }

    @Composable
    fun PlaybackControls(paddingValues: PaddingValues) {
        val isPlaying = viewModel.apolloPLayer.isPlaying.collectAsState()
        val buttonSize = 86.dp
        Box(
            modifier = Modifier.fillMaxSize(),
            content = {
                Row(
                    Modifier
                        .padding(paddingValues)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(26.dp)
                        )
                        .align(Alignment.BottomCenter)
                ) {
                    val scope = rememberCoroutineScope()
                    IconButton(
                        modifier = Modifier
                            .size(buttonSize)
                            .padding(start = 8.dp, top = 8.dp, end = 4.dp, bottom = 8.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = MaterialShapes.Square.toShape(),
                        onClick = {
                            if (viewModel.apolloPLayer.mediaController.hasPreviousMediaItem()) {
                                viewModel.apolloPLayer.mediaController.seekToPrevious()
                                viewModel.nowPlayingIndex.value =
                                    viewModel.apolloPLayer.mediaController.currentMediaItemIndex
                            }
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Rounded.SkipPrevious,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                    IconButton(
                        modifier = Modifier
                            .size(buttonSize)
                            .padding(vertical = 8.dp, horizontal = 4.dp),
                        onClick = {
                            if (isPlaying.value) {
                                viewModel.apolloPLayer.mediaController.pause()
                            } else {
                                if (viewModel.apolloPLayer.playerState.value == PlaybackState.STATE_NONE) {
                                    viewModel.apolloPLayer.mediaController.prepare()
                                    viewModel.apolloPLayer.mediaController.play()
                                } else {
                                    viewModel.apolloPLayer.mediaController.play()
                                }
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = MaterialShapes.Square.toShape(),
                        content = {
                            Icon(
                                imageVector = if (isPlaying.value) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                    IconButton(
                        modifier = Modifier
                            .size(buttonSize)
                            .padding(start = 4.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                        onClick = {
                            if (viewModel.apolloPLayer.mediaController.hasNextMediaItem()) {
                                viewModel.apolloPLayer.mediaController.seekToNext()
                                viewModel.nowPlayingIndex.value =
                                    viewModel.apolloPLayer.mediaController.currentMediaItemIndex
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = MaterialShapes.Square.toShape(),
                        content = {
                            Icon(
                                imageVector = Icons.Rounded.SkipNext,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                }
            }
        )

    }
}
