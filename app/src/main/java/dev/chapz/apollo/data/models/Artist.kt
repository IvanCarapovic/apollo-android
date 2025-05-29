package dev.chapz.apollo.data.models

import android.net.Uri

/**
 * Data class that represents the artist
 * @param id The id of the artist.
 * @param artist The name of the artist.
 * @param artworkUri The artist art.
 * @param numberOfAlbums The number of albums by this artist on the device.
 * @param numberOfTracks The number of tracks by this artist on the device.
 */
data class Artist(
    val id: Long,
    val artist: String = "Unknown artist",
    val artworkUri: Uri? = null,
    val numberOfAlbums: Int = -1,
    val numberOfTracks: Int = -1
)