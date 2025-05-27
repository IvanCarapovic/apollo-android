package dev.chapz.apollo.data.models // Ensure this matches your package structure

import android.net.Uri
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SongTest {

    // Helper to create a mocked Uri instance
    private fun mockUri(uriString: String): Uri {
        val mockedUri = mockk<Uri>()
        // Mocking toString() is useful for verifying in tests and for Song.toString()
        every { mockedUri.toString() } returns uriString
        // Add other common Uri behaviors if your Song class or tests might use them
        // For example:
        // every { mockedUri.scheme } returns "content"
        // every { mockedUri.path } returns "/media/external/audio/media/123"
        return mockedUri
    }

    @Before
    fun setUp() {
        // Mock the static Uri.parse method for all tests in this class
        // This is useful if any part of your code or test setup might call Uri.parse
        mockkStatic(Uri::class)
    }

    @After
    fun tearDown() {
        // Good practice to unmock static mocks, though for simple Uri.parse it might not always
        // cause issues if not unmocked. For more complex scenarios, it's essential.
        unmockkStatic(Uri::class) // You can uncomment if needed
    }

    // --- Property Getter Tests ---

    @Test
    fun `uri returns correct value`() {
        val songUriString = "content://media/external/audio/media/1"
        val mockSongUri = mockUri(songUriString)
        every { Uri.parse(songUriString) } returns mockSongUri // Stub if Uri.parse is used

        val song = Song(
            uri = mockSongUri,
            title = "Test Song",
            artist = "Test Artist",
            artistId = 1,
            album = "Test Album",
            albumId = 10,
            albumArtUri = null,
            duration = 180000L,
            track = 1,
            year = 2023,
            path = "/sdcard/music/test.mp3"
        )
        assertEquals(mockSongUri, song.uri)
    }

    @Test
    fun `title returns correct value when not null`() {
        val song = Song(mockUri("uri:song"), "Song Title", "Artist", 1, "Album", 10, null, 180L, 1, 2023, "/path")
        assertEquals("Song Title", song.title)
    }

    @Test
    fun `artist returns correct value when not null`() {
        val song = Song(mockUri("uri:song"), "Title", "Artist Name", 1, "Album", 10, null, 180L, 1, 2023, "/path")
        assertEquals("Artist Name", song.artist)
    }

    @Test
    fun `artistId returns correct value`() {
        val song = Song(mockUri("uri:song"), "Title", "Artist", 123, "Album", 10, null, 180L, 1, 2023, "/path")
        assertEquals(123, song.artistId)
    }

    @Test
    fun `album returns correct value when not null`() {
        val song = Song(mockUri("uri:song"), "Title", "Artist", 1, "Album Name", 10, null, 180L, 1, 2023, "/path")
        assertEquals("Album Name", song.album)
    }

    @Test
    fun `albumId returns correct value`() {
        val song = Song(mockUri("uri:song"), "Title", "Artist", 1, "Album", 101, null, 180L, 1, 2023, "/path")
        assertEquals(101, song.albumId)
    }

    @Test
    fun `albumArtUri returns correct value when not null`() {
        val artUriString = "content://media/external/audio/albumart/10"
        val mockArtUri = mockUri(artUriString)
        every { Uri.parse(artUriString) } returns mockArtUri

        val song = Song(mockUri("uri:song"), "Title", "Artist", 1, "Album", 10, mockArtUri, 180L, 1, 2023, "/path")
        assertEquals(mockArtUri, song.albumArtUri)
    }

    @Test
    fun `albumArtUri returns null when null`() {
        val song = Song(mockUri("uri:song"), "Title", "Artist", 1, "Album", 10, null, 180L, 1, 2023, "/path")
        assertNull(song.albumArtUri)
    }

    @Test
    fun `duration returns correct value when not null`() {
        val song = Song(mockUri("uri:song"), "Title", "Artist", 1, "Album", 10, null, 240000L, 1, 2023, "/path")
        assertEquals(240000L, song.duration)
    }

    @Test
    fun `duration returns null when null`() {
        val song = Song(mockUri("uri:song"), "Title", "Artist", 1, "Album", 10, null, null, 1, 2023, "/path")
        assertNull(song.duration)
    }

    @Test
    fun `track returns correct value when not null`() {
        val song = Song(mockUri("uri:song"), "Title", "Artist", 1, "Album", 10, null, 180L, 5, 2023, "/path")
        assertEquals(5, song.track)
    }

    @Test
    fun `track returns null when null`() {
        val song = Song(mockUri("uri:song"), "Title", "Artist", 1, "Album", 10, null, 180L, null, 2023, "/path")
        assertNull(song.track)
    }

    @Test
    fun `year returns correct value when not null`() {
        val song = Song(mockUri("uri:song"), "Title", "Artist", 1, "Album", 10, null, 180L, 1, 1999, "/path")
        assertEquals(1999, song.year)
    }

    @Test
    fun `year returns null when null`() {
        val song = Song(mockUri("uri:song"), "Title", "Artist", 1, "Album", 10, null, 180L, 1, null, "/path")
        assertNull(song.year)
    }

    @Test
    fun `path returns correct value when not null`() {
        val song = Song(mockUri("uri:song"), "Title", "Artist", 1, "Album", 10, null, 180L, 1, 2023, "/music/song.mp3")
        assertEquals("/music/song.mp3", song.path)
    }

    @Test
    fun `path returns null when null`() {
        val song = Song(mockUri("uri:song"), "Title", "Artist", 1, "Album", 10, null, 180L, 1, 2023, null)
        assertNull(song.path)
    }

    // --- equals() and hashCode() Tests ---

    @Test
    fun `equals returns true for identical instances`() {
        val songUri = mockUri("uri:song1")
        val artUri = mockUri("uri:art1")

        val song1 = Song(songUri, "T", "A", 1, "Al", 10, artUri, 1L, 1, 2000, "/p")
        val song2 = Song(songUri, "T", "A", 1, "Al", 10, artUri, 1L, 1, 2000, "/p")
        assertEquals(song1, song2)
    }

    @Test
    fun `equals returns false for different main uri`() {
        val songUri1 = mockUri("uri:song1")
        val songUri2 = mockUri("uri:song2")
        val artUri = mockUri("uri:art1")

        val song1 = Song(songUri1, "T", "A", 1, "Al", 10, artUri, 1L, 1, 2000, "/p")
        val song2 = Song(songUri2, "T", "A", 1, "Al", 10, artUri, 1L, 1, 2000, "/p")
        assertNotEquals(song1, song2)
    }

    @Test
    fun `equals returns false for different title`() {
        val songUri = mockUri("uri:song1")
        val song1 = Song(songUri, "Title1", "A", 1, "Al", 10, null, 1L, 1, 2000, "/p")
        val song2 = Song(songUri, "Title2", "A", 1, "Al", 10, null, 1L, 1, 2000, "/p")
        assertNotEquals(song1, song2)
    }

    @Test
    fun `equals returns false for different albumArtUri (null vs non-null)`() {
        val songUri = mockUri("uri:song1")
        val artUri = mockUri("uri:art1")
        val song1 = Song(songUri, "T", "A", 1, "Al", 10, null, 1L, 1, 2000, "/p")
        val song2 = Song(songUri, "T", "A", 1, "Al", 10, artUri, 1L, 1, 2000, "/p")
        assertNotEquals(song1, song2)
    }

    @Test
    fun `hashCode is consistent for equal objects`() {
        val songUri = mockUri("uri:song1")
        val artUri = mockUri("uri:art1")

        val song1 = Song(songUri, "T", "A", 1, "Al", 10, artUri, 1L, 1, 2000, "/p")
        val song2 = Song(songUri, "T", "A", 1, "Al", 10, artUri, 1L, 1, 2000, "/p")
        assertEquals(song1.hashCode(), song2.hashCode())
    }

    // --- copy() Tests ---

    @Test
    fun `copy creates a new instance with identical properties`() {
        val songUri = mockUri("uri:song1")
        val artUri = mockUri("uri:art1")
        val original = Song(songUri, "T", "A", 1, "Al", 10, artUri, 1L, 1, 2000, "/p")
        val copied = original.copy()

        assertEquals(original, copied)
        assertNotSame(original, copied) // Ensure they are different instances
    }

    @Test
    fun `copy allows changing a specific property (e_g_ title)`() {
        val songUri = mockUri("uri:song1")
        val original = Song(songUri, "Old Title", "A", 1, "Al", 10, null, 1L, 1, 2000, "/p")
        val copied = original.copy(title = "New Title")

        assertEquals("New Title", copied.title)
        assertEquals(original.artist, copied.artist) // Check other properties remain
        assertNotEquals(original, copied)
    }

    @Test
    fun `copy allows changing albumArtUri from null to non-null`() {
        val songUri = mockUri("uri:song1")
        val newArtUri = mockUri("uri:newArt")
        val original = Song(songUri, "T", "A", 1, "Al", 10, null, 1L, 1, 2000, "/p")
        val copied = original.copy(albumArtUri = newArtUri)

        assertEquals(newArtUri, copied.albumArtUri)
        assertNull(original.albumArtUri)
    }

    // --- toString() Tests ---

    @Test
    fun `toString contains property values`() {
        val songUriString = "content://media/external/audio/media/song123"
        val artUriString = "content://media/external/audio/albumart/album456"
        val mockSongUri = mockUri(songUriString)
        val mockArtUri = mockUri(artUriString)

        // Stub Uri.parse if it's used internally by any helper or setup
        every { Uri.parse(songUriString) } returns mockSongUri
        every { Uri.parse(artUriString) } returns mockArtUri


        val song = Song(
            uri = mockSongUri,
            title = "Cool Song",
            artist = "Great Artist",
            artistId = 7,
            album = "Awesome Album",
            albumId = 77,
            albumArtUri = mockArtUri,
            duration = 210000L,
            track = 3,
            year = 2021,
            path = "/storage/emulated/0/Music/cool_song.mp3"
        )
        val stringRepresentation = song.toString()

        assertTrue(stringRepresentation.contains("uri=$songUriString"))
        assertTrue(stringRepresentation.contains("title=Cool Song"))
        assertTrue(stringRepresentation.contains("artist=Great Artist"))
        assertTrue(stringRepresentation.contains("artistId=7"))
        assertTrue(stringRepresentation.contains("album=Awesome Album"))
        assertTrue(stringRepresentation.contains("albumId=77"))
        assertTrue(stringRepresentation.contains("albumArtUri=$artUriString"))
        assertTrue(stringRepresentation.contains("duration=210000"))
        assertTrue(stringRepresentation.contains("track=3"))
        assertTrue(stringRepresentation.contains("year=2021"))
        assertTrue(stringRepresentation.contains("path=/storage/emulated/0/Music/cool_song.mp3"))
    }

    @Test
    fun `toString handles null properties correctly`() {
        val songUriString = "content://media/external/audio/media/song_minimal"
        val mockSongUri = mockUri(songUriString)
        every { Uri.parse(songUriString) } returns mockSongUri

        val song = Song(
            uri = mockSongUri,
            artistId = 1, // Non-nullable
            albumId = 1,  // Non-nullable
            albumArtUri = null,
            duration = null,
            track = null,
            year = null,
            path = null
        )
        val stringRepresentation = song.toString()

        assertTrue(stringRepresentation.contains("uri=$songUriString"))
        assertTrue(stringRepresentation.contains("title=Unknown title"))
        assertTrue(stringRepresentation.contains("artist=Unknown artist"))
        assertTrue(stringRepresentation.contains("album=Unknown album"))
        assertTrue(stringRepresentation.contains("albumArtUri=null"))
        assertTrue(stringRepresentation.contains("duration=null"))
        assertTrue(stringRepresentation.contains("track=null"))
        assertTrue(stringRepresentation.contains("year=null"))
        assertTrue(stringRepresentation.contains("path=null"))
    }
}