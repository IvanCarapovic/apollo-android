package dev.chapz.apollo.data.models

/**
 * Object representing a genre.
 *
 * @param id unique local ID of the genre.
 * @param genre the name of the genre.
 * @param numberOfSongs the number of songs in the genre.
 */
data class Genre(
    val id: Long,
    val genre: String? = null,
    val numberOfSongs: Int = -1
)

