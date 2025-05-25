package dev.chapz.apollo.ui.songs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import dev.chapz.apollo.data.library.Library
import dev.chapz.apollo.data.models.Song
import dev.chapz.apollo.permissions.AudioPermissionRequestButton
import dev.chapz.apollo.permissions.NotificationPermissionRequestButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SongList(paddingValues: PaddingValues) {
    val scope = rememberCoroutineScope()
    val library = Library(LocalContext.current.contentResolver)
    val songs = remember { mutableStateListOf<Song>() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(songs.size) { index ->
                SongItem(songs[index])
            }
        }
        AudioPermissionRequestButton { scope.launch { songs.addAll(library.getSongs()) } }
        NotificationPermissionRequestButton()
    }
}

@Preview
@Composable
fun SongItem(@PreviewParameter(SongProvider::class) song: Song) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 12.dp,
                horizontal = 24.dp
            )
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(
                    RoundedCornerShape(4.dp)
                ),
            model = ImageRequest.Builder(LocalContext.current)
                .size(64)
                .decoderDispatcher(Dispatchers.IO)
                .allowRgb565(true)
                .diskCachePolicy(CachePolicy.ENABLED)
                .diskCacheKey(song.albumArtUri.toString())
                .data(song.albumArtUri)
                .build(),
            contentDescription = null,

            )
        Column(
            modifier = Modifier.padding(
                start = 16.dp
            ),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = song.title ?: "",
                color = Color.White,
                fontSize = 16.sp
            )
            Text(
                text = song.artist ?: "",
                fontSize = 14.sp,
                color = Color.Gray
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