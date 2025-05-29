package dev.chapz.apollo.ui.artists

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import dev.chapz.apollo.data.library.Library
import dev.chapz.apollo.data.models.Album
import dev.chapz.apollo.data.models.Artist
import dev.chapz.apollo.permissions.AudioPermissionRequestButton
import dev.chapz.apollo.permissions.NotificationPermissionRequestButton
import kotlinx.coroutines.launch

@Composable
fun ArtistsList(paddingValues: PaddingValues) {
    val scope = rememberCoroutineScope()
    val library = Library(LocalContext.current.contentResolver)
    val artists = remember { mutableStateListOf<Artist>() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(artists.size) { index ->
                ArtistItem(artists[index])
            }
        }
        AudioPermissionRequestButton { scope.launch { artists.addAll(library.getArtists()) } }
        NotificationPermissionRequestButton()
    }
}

@Preview
@Composable
fun ArtistItem(@PreviewParameter(AlbumProvider::class) artist: Artist) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 12.dp,
                horizontal = 24.dp
            )
    ) {
//        SubcomposeAsyncImage(
//            modifier = Modifier
//                .size(48.dp)
//                .clip(
//                    RoundedCornerShape(4.dp)
//                ),
//            model = ImageRequest.Builder(LocalContext.current)
//                .size(64)
//                .decoderDispatcher(Dispatchers.IO)
//                .allowRgb565(true)
//                .diskCachePolicy(CachePolicy.ENABLED)
//                .diskCacheKey(artist.artworkUri.toString())
//                .data(artist.artworkUri)
//                .build(),
//            contentDescription = null,
//
//            )
        Column(
            modifier = Modifier.padding(
                start = 16.dp
            ),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = artist.artist,
                color = Color.White,
                fontSize = 16.sp
            )
            Text(
                text = artist.numberOfAlbums.toString() + " albums",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

class AlbumProvider : PreviewParameterProvider<Album> {
    override val values: Sequence<Album> = sequenceOf(
        Album(
            id = 0L,
            title = "Fuel",
            artist = "Eminem feat. JID",
            artistId = 2,
            artworkUri = "http://album/art/1".toUri(),
            10
        )
    )
}