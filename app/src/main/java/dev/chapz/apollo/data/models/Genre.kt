package dev.chapz.apollo.data.models

/**
 * Object representing a genre.
 *
 * @param genreId unique local ID of the genre.
 * @param name the name of the genre.
 * @param numberOfSongs the number of songs in the genre.
 */
data class Genre(
    val genreId: Long,
    val name: String?,
    val numberOfSongs: Int
)

