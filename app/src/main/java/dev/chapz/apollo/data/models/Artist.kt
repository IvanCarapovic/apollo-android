package dev.chapz.apollo.data.models

import android.net.Uri

/**
 * Data class that represents the artist
 * @param id The id of the artist.
 * @param name The name of the artist.
 * @param artworkUri The artist art.
 * @param numberOfAlbums The number of albums by this artist on the device.
 * @param numberOfTracks The number of tracks by this artist on the device.
 */
data class Artist(
    val artistId: Int,
    val name: String?,
    val artworkUri: Uri?,
    val numberOfAlbums: Int,
    val numberOfTracks: Int
)