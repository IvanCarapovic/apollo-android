package dev.chapz.apollo.data.library

import android.net.Uri

data class Song (
    val uri: Uri,
    val title: String?,
    val artist: String?,
    val album: String?,
    val albumId: Long?,
    val duration: Long?,
    val track: Int?,
    val year: Int?,
    val path: String?
)