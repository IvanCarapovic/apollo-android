package dev.chapz.apollo.data.models

import android.net.Uri

/**
 * Object representing a song.
 *
 * @param uri Unique URI to the song file on the device
 * @param title the title of the song
 * @param artist the names of featured artists on the song
 * @param artistId the unique local ID of the featured artists on the song
 * @param album the name of the album the song is from
 * @param albumId the unique local ID of the album the song is from
 * @param albumArtUri the URI of the album artwork
 * @param duration the duration of the song in milliseconds
 * @param track the track number of the song on the album
 * @param year the year the song was released
 * @param path the path to the song file on the device
 * */
data class Song(
    val uri: Uri,
    val title: String = "Unknown title",
    val artist: String = "Unknown artist",
    val artistId: Long = -1,
    val album: String = "Unknown album",
    val albumId: Long = -1,
    val albumArtUri: Uri? = null,
    val duration: Long? = 0,
    val track: Int? = 0,
    val year: Int? = 0,
    val path: String? = null
)