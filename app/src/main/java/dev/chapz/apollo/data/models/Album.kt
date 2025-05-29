package dev.chapz.apollo.data.models

import android.net.Uri

/**
 * Object representing an audio album.
 *
 * @param id unique local ID of the album.
 * @param title the title of the album.
 * @param artist the name of the artist(s) associated with the album.
 * @param artistId unique local ID of the artist(s) associated with the album.
 * @param artworkUri the URI of the album artwork.
 * @param numberOfSongs the number of songs in the album.
 * */
data class Album(
    val id: Long,
    val title: String = "Unknown album",
    val artist: String = "Unknown artist",
    val artistId: Long = -1,
    val artworkUri: Uri? = null,
    val numberOfSongs: Int = -1,
)