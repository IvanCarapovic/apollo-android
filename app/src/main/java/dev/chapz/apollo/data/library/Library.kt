package dev.chapz.apollo.data.library

import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.core.net.toUri
import dev.chapz.apollo.data.models.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Read local music collection and its metadata from the device such as
 * [dev.chapz.apollo.data.models.Song]s.
 *
 * All methods follow the same pattern:
 *
 * * prepare the query of metadata that needs to be read and the columns in [MediaStore]
 * * query the [ContentResolver] using the prepared metadata query and receive a [Cursor]
 * * get column indexes for the individual metadata
 * * iterate through the result, create data objects and add them to a list
 *
 * @param contentResolver The content resolver used to access the device's media store.
 * */
class Library(private val contentResolver: ContentResolver) {

    /**
     * Fetch a list of local songs from the device.
     *
     * @return a list of [dev.chapz.apollo.data.models.Song] objects with queried metadata
     * */
    suspend fun getSongs(): List<Song> = withContext(Dispatchers.IO) {
        val songs = mutableListOf<Song>()
        val collection = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.TRACK,
            MediaStore.Audio.Media.YEAR,
            MediaStore.Audio.Media.DATA
        )

        val query = contentResolver.query(collection, projection, null, null, null)

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val artistIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID)
            val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val trackColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TRACK)
            val yearColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
                songs.add(
                    Song(
                        uri,
                        cursor.getString(titleColumn),
                        cursor.getString(artistColumn),
                        cursor.getLong(artistIdColumn),
                        cursor.getString(albumColumn),
                        cursor.getLong(albumIdColumn),
                        getAlbumArtworkUri(cursor.getLong(albumIdColumn)),
                        cursor.getLong(durationColumn),
                        cursor.getInt(trackColumn),
                        cursor.getInt(yearColumn),
                        cursor.getString(dataColumn)
                    )
                )
            }
        }
        return@withContext songs
    }

    /**
     * Fetch the album art URI of the given album.
     *
     * @param albumId The ID of the album.
     * @return The URI of the album art.
     * */
    private fun getAlbumArtworkUri(albumId: Long): Uri {
        val albumUri = ContentUris.withAppendedId("content://media/external/audio/albumart".toUri(), albumId)
        return albumUri
    }
}

