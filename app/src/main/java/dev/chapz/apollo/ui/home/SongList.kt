package dev.chapz.apollo.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import dev.chapz.apollo.data.library.Library
import dev.chapz.apollo.data.library.Song
import dev.chapz.apollo.permissions.AudioPermissionRequestButton
import dev.chapz.apollo.permissions.NotificationPermissionRequestButton
import kotlinx.coroutines.launch

@Composable
fun SongList(paddingValues: PaddingValues) {
    val scope = rememberCoroutineScope()
    val songs = remember { mutableStateListOf<Song>() }
    val library = Library(LocalContext.current.contentResolver)

    Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
        AudioPermissionRequestButton { scope.launch { songs.addAll(library.getSongs()) } }
        NotificationPermissionRequestButton()
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(songs.size) { index ->
                SongItem(songs[index])
            }
        }
    }
}

@Preview
@Composable
fun SongItem(@PreviewParameter(SongProvider::class) song: Song) {
    Row(modifier = Modifier.fillMaxWidth()) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .size(128)
                .data(song.albumArtUri)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.padding(8.dp),
        )
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = song.title ?: "",
                fontSize = 18.sp
            )
            Text(text = song.artist ?: "",
                fontSize = 12.sp)
        }
    }
}

class SongProvider : PreviewParameterProvider<Song> {
    override val values: Sequence<Song> = sequenceOf(
        Song(
            uri = "http://hello/kitty".toUri(),
            title = "Fuel",
            artist = "Eminem feat. JID",
            album = "The Eminem Show",
            albumArtUri = "http://album/art/1".toUri(),
            duration = 1000 * 3 * 60L + 1000 * 25L,
            track = 2,
            year = 2024,
            path = "content://something"
        )
    )
}