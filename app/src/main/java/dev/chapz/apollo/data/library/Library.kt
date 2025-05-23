package dev.chapz.apollo.data.library

import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Read local music collection and its metadata from the device such as
 * [Song]s, [Album]s, artists, genres and playlists.
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
     * @return a list of [Song] objects with queried metadata
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
                        cursor.getInt(artistIdColumn),
                        cursor.getString(albumColumn),
                        cursor.getInt(albumIdColumn),
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
     * Fetch a list of local albums from the device.
     *
     * @return a list of [Album] objects with queried metadata
     * */
    suspend fun getAlbums(): List<Album> = withContext(Dispatchers.IO) {
        val albums = mutableListOf<Album>()
        val collection = MediaStore.Audio.Albums.getContentUri(MediaStore.VOLUME_EXTERNAL)

        val projection = arrayOf(
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.ARTIST,
            MediaStore.Audio.Albums.ARTIST_ID,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,
        )

        val query = contentResolver.query(collection, projection, null, null, null)

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ARTIST)
            val artistIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ARTIST_ID)
            val numSongsColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.NUMBER_OF_SONGS)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val artworkUri = getAlbumArtworkUri(id)

                albums.add(
                    Album(
                        id,
                        cursor.getString(titleColumn),
                        cursor.getString(artistColumn),
                        cursor.getInt(artistIdColumn),
                        artworkUri,
                        cursor.getInt(numSongsColumn),
                    )
                )
            }
        }
        return@withContext albums
    }

    /**
     * Fetch a list of local artists from the device.
     *
     * @return a list of [Artist] objects with queried metadata
     * */
    suspend fun getArtists(): List<Artist> = withContext(Dispatchers.IO) {
        val artists = mutableListOf<Artist>()
        val collection = MediaStore.Audio.Artists.getContentUri(MediaStore.VOLUME_EXTERNAL)

        val projection = arrayOf(
            MediaStore.Audio.Artists._ID,
            MediaStore.Audio.Artists.ARTIST,
            MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
            MediaStore.Audio.Artists.NUMBER_OF_TRACKS
        )

        val query = contentResolver.query(collection, projection, null, null, null)

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST)
            val numAlbumsColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS)
            val numTracksColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_TRACKS)

            while (cursor.moveToNext()) {
                val id = cursor.getInt(idColumn)
                val artworkUri = null // load the artwork of one of the songs or albums of the artist

                artists.add(
                    Artist(
                        id,
                        cursor.getString(nameColumn),
                        artworkUri,
                        cursor.getInt(numAlbumsColumn),
                        cursor.getInt(numTracksColumn)
                    )
                )
            }
        }
        return@withContext artists
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