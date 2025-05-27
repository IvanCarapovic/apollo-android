package dev.chapz.apollo.ui.songs

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import dev.chapz.apollo.data.library.Library
import dev.chapz.apollo.data.models.Song
import dev.chapz.apollo.getMediaController
import dev.chapz.apollo.permissions.AudioPermissionRequestButton
import dev.chapz.apollo.permissions.NotificationPermissionRequestButton
import kotlinx.coroutines.launch

@Composable
fun SongList(paddingValues: PaddingValues) {
    val scope = rememberCoroutineScope()
    val library = Library(LocalContext.current.contentResolver)
    val songs = remember { mutableStateListOf<Song>() }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(top = paddingValues.calculateTopPadding()),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                )
        ) {
            items(songs.size) { index ->
                SongItem(
                    song = songs[index],
                    curveTopShape = index == 0,
                    curveBottomShape = index == songs.size - 1,
                )
            }
        }
        AudioPermissionRequestButton { scope.launch { songs.addAll(library.getSongs().sortedBy { it.title }) } }
        NotificationPermissionRequestButton()
    }
}

@Preview
@Composable
fun SongItem(
    @PreviewParameter(SongProvider::class) song: Song,
    curveTopShape: Boolean = false,
    curveBottomShape: Boolean = false,
) {
    val mediaController = LocalContext.current.getMediaController()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(86.dp)
            .padding(vertical = 1.dp)
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
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
                    val mediaItem = MediaItem.fromUri(song.uri)
                    mediaItem.mediaMetadata = MediaMetadata.Builder()
                        .setTitle(song.title)
                        .setArtist(song.artist)
                        .setAlbumTitle(song.album)
                        .setAlbumArtist(song.artist)
                        .setArtworkUri(song.albumArtUri)
                        .setIsPlayable(true)
                        .setIsBrowsable(false)
                        .build()
                    mediaController.setMediaItem(mediaItem)
                    mediaController.prepare()
                    mediaController.play()
                })
    ) {
//        SubcomposeAsyncImage(
//            modifier = Modifier
//                .size(64.dp)
//                .clip(
//                    RoundedCornerShape(8.dp)
//                ),
//            model = ImageRequest.Builder(LocalContext.current)
//                .size(64)
//                .decoderDispatcher(Dispatchers.IO)
//                .allowRgb565(true)
//                .diskCachePolicy(CachePolicy.ENABLED)
//                .diskCacheKey(song.albumArtUri.toString())
//                .data(song.albumArtUri)
//                .build(),
//            contentDescription = null,
//
//            )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        )
        {
            Text(
                text = song.title,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = song.artist,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.alpha(0.8f)
            )
        }
    }
}

class SongProvider : PreviewParameterProvider<Song> {
    override val values: Sequence<Song> = sequenceOf(
        Song(
            uri = "http://hello/kitty".toUri(),
            title = "Fuel",
            artist = "Eminem feat. JID",
            artistId = 2,
            album = "The Eminem Show",
            albumId = 1,
            albumArtUri = "http://album/art/1".toUri(),
            duration = 1000 * 3 * 60L + 1000 * 25L,
            track = 2,
            year = 2024,
            path = "content://something"
        )
    )
}