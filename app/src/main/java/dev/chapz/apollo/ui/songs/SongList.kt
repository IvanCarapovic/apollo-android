package dev.chapz.apollo.ui.songs

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import dev.chapz.apollo.app.MainViewModel
import dev.chapz.apollo.data.library.Library
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import kotlin.system.measureTimeMillis

@Composable
fun SongList(
    paddingValues: PaddingValues,
    viewModel: MainViewModel = koinInject(),
    library: Library = koinInject()
) {
    val scope = rememberCoroutineScope()
    val songs = remember { mutableStateListOf<MediaItem>() }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                )
        ) {
            items(songs.size) { index ->
                SongItem(
                    songs = songs,
                    index = index,
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        scope.launch {
            val time = measureTimeMillis {
                songs.addAll(
                    library.getSongs()
                        .sortedBy { it.title }
                        .map { song ->
                            val mediaItem = MediaItem.fromUri(song.uri)
                            mediaItem.mediaMetadata = MediaMetadata.Builder()
                                .setTitle(song.title)
                                .setAlbumTitle(song.album)
                                .setArtist(song.artist)
                                .setIsPlayable(true)
                                .setIsBrowsable(false)
                                .setDisplayTitle(song.title)
                                //.setArtworkUri(song.albumArtUri)
                                .build()
                            mediaItem
                        })
            }
            viewModel.player.mediaController.setMediaItems(songs, true)
            viewModel.player.mediaController.prepare()
            Log.i("PERFORMANCE", "Building songs took $time ms")
        }
    }
}

@Composable
fun SongItem(
    songs: List<MediaItem>,
    index: Int,
    viewModel: MainViewModel = koinInject(),
    curveTopShape: Boolean = index == 0,
    curveBottomShape: Boolean = index == songs.size - 1,
) {
    val nowPlayingIndex = viewModel.player.nowPlayingIndex.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(86.dp)
            .padding(vertical = 1.dp)
            .background(
                color = if (index == nowPlayingIndex.value) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.secondaryContainer
                },
                shape = RoundedCornerShape(
                    topStart = if (curveTopShape) 24.dp else 4.dp,
                    topEnd = if (curveTopShape) 24.dp else 4.dp,
                    bottomStart = if (curveBottomShape) 24.dp else 4.dp,
                    bottomEnd = if (curveBottomShape) 24.dp else 4.dp
                )
            )
            .clickable(
                enabled = true,
                onClick = {
                    coroutineScope.launch {
                        viewModel.player.mediaController.seekTo(index, 0)
                        viewModel.player.mediaController.play()
                    }
                })
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        )
        {
            Text(
                text = songs[index].mediaMetadata.title.toString(),
                color = if (index == nowPlayingIndex.value) {
                    MaterialTheme.colorScheme.primaryContainer
                } else MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = songs[index].mediaMetadata.artist.toString(),
                fontSize = 14.sp,
                color = if (index == nowPlayingIndex.value) {
                    MaterialTheme.colorScheme.primaryContainer
                } else MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.alpha(0.8f)
            )
        }
    }
}