package dev.chapz.apollo.data.models

import android.net.Uri

/**
 * Object representing an audio album.
 *
 * @param albumId unique local ID of the album.
 * @param title the title of the album.
 * @param artist the name of the artist(s) associated with the album.
 * @param artistId unique local ID of the artist(s) associated with the album.
 * @param artworkUri the URI of the album artwork.
 * @param numberOfSongs the number of songs in the album.
 * */
data class Album(
    val albumId: Long,
    val title: String,
    val artist: String,
    val artistId: Long,
    val artworkUri: Uri?,
    val numberOfSongs: Int,
)