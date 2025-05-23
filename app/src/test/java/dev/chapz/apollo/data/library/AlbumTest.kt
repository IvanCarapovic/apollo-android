package dev.chapz.apollo.data.library // Ensure this matches your package structure

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

class AlbumTest {

    // Helper to create a mocked Uri instance
    private fun mockUri(uriString: String): Uri {
        val mockedUri = mockk<Uri>()
        every { mockedUri.toString() } returns uriString // For toString() tests
        // Add other behavior if needed, e.g., every { mockedUri.scheme } returns "content"
        return mockedUri
    }

    @Before
    fun setUp() {
        // Mock the static Uri.parse method for all tests in this class
        mockkStatic(Uri::class)
    }

    @After
    fun tearDown() {
        // Unmock static methods after each test to avoid interference
        // This is good practice, though for Uri.parse it might not always be strictly necessary
        // if your tests are simple. For more complex static mocking, it's crucial.
        unmockkStatic(Uri::class) // Not strictly needed if only mocking parse for return
    }

    @Test
    fun `albumId returns correct value`() {
        val album = Album(albumId = 1L, title = "Test Title", artist = "Test Artist", artistId = 10, artworkUri = null, numberOfSongs = 5)
        assertEquals(1L, album.albumId)
    }

    @Test
    fun `title returns correct value`() {
        val album = Album(albumId = 1L, title = "Test Title", artist = "Test Artist", artistId = 10, artworkUri = null, numberOfSongs = 5)
        assertEquals("Test Title", album.title)
    }

    @Test
    fun `artist returns correct value`() {
        val album = Album(albumId = 1L, title = "Test Title", artist = "Test Artist", artistId = 10, artworkUri = null, numberOfSongs = 5)
        assertEquals("Test Artist", album.artist)
    }

    @Test
    fun `artistId returns correct value`() {
        val album = Album(albumId = 1L, title = "Test Title", artist = "Test Artist", artistId = 10, artworkUri = null, numberOfSongs = 5)
        assertEquals(10, album.artistId)
    }

    @Test
    fun `artworkUri returns correct value when not null`() {
        val uriString = "content://media/external/audio/albumart/1"
        val mockUriInstance = mockUri(uriString)
        // Stub the Uri.parse call to return our mocked Uri
        every { Uri.parse(uriString) } returns mockUriInstance

        val album = Album(albumId = 1L, title = "Test Title", artist = "Test Artist", artistId = 10, artworkUri = mockUriInstance, numberOfSongs = 5)
        assertEquals(mockUriInstance, album.artworkUri)
    }

    @Test
    fun `artworkUri returns null when null`() {
        val album = Album(albumId = 1L, title = "Test Title", artist = "Test Artist", artistId = 10, artworkUri = null, numberOfSongs = 5)
        assertNull(album.artworkUri)
    }

    @Test
    fun `numberOfSongs returns correct value`() {
        val album = Album(albumId = 1L, title = "Test Title", artist = "Test Artist", artistId = 10, artworkUri = null, numberOfSongs = 5)
        assertEquals(5, album.numberOfSongs)
    }

    @Test
    fun `equals returns true for identical instances`() {
        val uriString = "content://media/external/audio/albumart/1"
        val mockUriInstance = mockUri(uriString)
        every { Uri.parse(uriString) } returns mockUriInstance // Ensure parse is stubbed if it's called internally

        val album1 = Album(albumId = 1L, title = "Title", artist = "Artist", artistId = 1, artworkUri = mockUriInstance, numberOfSongs = 10)
        val album2 = Album(albumId = 1L, title = "Title", artist = "Artist", artistId = 1, artworkUri = mockUriInstance, numberOfSongs = 10)
        assertEquals(album1, album2)
    }

    @Test
    fun `equals returns false for different albumId`() {
        val album1 = Album(albumId = 1L, title = "Title", artist = "Artist", artistId = 1, artworkUri = null, numberOfSongs = 10)
        val album2 = Album(albumId = 2L, title = "Title", artist = "Artist", artistId = 1, artworkUri = null, numberOfSongs = 10)
        assertNotEquals(album1, album2)
    }

    @Test
    fun `equals returns false for different title`() {
        val album1 = Album(albumId = 1L, title = "Title1", artist = "Artist", artistId = 1, artworkUri = null, numberOfSongs = 10)
        val album2 = Album(albumId = 1L, title = "Title2", artist = "Artist", artistId = 1, artworkUri = null, numberOfSongs = 10)
        assertNotEquals(album1, album2)
    }

    @Test
    fun `equals returns false for different artworkUri (null vs non-null)`() {
        val uriString = "content://media/external/audio/albumart/1"
        val mockUriInstance = mockUri(uriString)
        every { Uri.parse(uriString) } returns mockUriInstance

        val album1 = Album(albumId = 1L, title = "Title", artist = "Artist", artistId = 1, artworkUri = null, numberOfSongs = 10)
        val album2 = Album(albumId = 1L, title = "Title", artist = "Artist", artistId = 1, artworkUri = mockUriInstance, numberOfSongs = 10)
        assertNotEquals(album1, album2)
    }

    @Test
    fun `equals returns false for different non-null artworkUri`() {
        val uriString1 = "content://media/external/audio/albumart/1"
        val mockUriInstance1 = mockUri(uriString1)
        every { Uri.parse(uriString1) } returns mockUriInstance1

        val uriString2 = "content://media/external/audio/albumart/2"
        val mockUriInstance2 = mockUri(uriString2)
        every { Uri.parse(uriString2) } returns mockUriInstance2

        val album1 = Album(albumId = 1L, title = "Title", artist = "Artist", artistId = 1, artworkUri = mockUriInstance1, numberOfSongs = 10)
        val album2 = Album(albumId = 1L, title = "Title", artist = "Artist", artistId = 1, artworkUri = mockUriInstance2, numberOfSongs = 10)
        assertNotEquals(album1, album2) // This relies on the mockUri instances being different
    }


    @Test
    fun `hashCode is consistent for equal objects`() {
        val uriString = "content://media/external/audio/albumart/1"
        val mockUriInstance = mockUri(uriString)
        every { Uri.parse(uriString) } returns mockUriInstance

        val album1 = Album(albumId = 1L, title = "Title", artist = "Artist", artistId = 1, artworkUri = mockUriInstance, numberOfSongs = 10)
        val album2 = Album(albumId = 1L, title = "Title", artist = "Artist", artistId = 1, artworkUri = mockUriInstance, numberOfSongs = 10)
        assertEquals(album1.hashCode(), album2.hashCode())
    }

    @Test
    fun `hashCode is different for unequal objects (usually)`() {
        val album1 = Album(albumId = 1L, title = "Title1", artist = "Artist", artistId = 1, artworkUri = null, numberOfSongs = 10)
        val album2 = Album(albumId = 2L, title = "Title2", artist = "ArtistB", artistId = 2, artworkUri = null, numberOfSongs = 12)
        assertNotEquals(album1.hashCode(), album2.hashCode())
    }

    @Test
    fun `copy creates a new instance with identical properties`() {
        val uriString = "content://media/external/audio/albumart/1"
        val mockUriInstance = mockUri(uriString)
        every { Uri.parse(uriString) } returns mockUriInstance

        val original = Album(albumId = 1L, title = "Original Title", artist = "Original Artist", artistId = 10, artworkUri = mockUriInstance, numberOfSongs = 5)
        val copied = original.copy()

        assertEquals(original, copied)
        assertNotSame(original, copied)
    }

    @Test
    fun `copy allows changing a specific property`() {
        val original = Album(albumId = 1L, title = "Original Title", artist = "Original Artist", artistId = 10, artworkUri = null, numberOfSongs = 5)
        val copiedWithNewTitle = original.copy(title = "New Title")

        assertEquals(1L, copiedWithNewTitle.albumId)
        assertEquals("New Title", copiedWithNewTitle.title)
        assertEquals("Original Artist", copiedWithNewTitle.artist)
        // ... assertions for other properties
        assertNotEquals(original, copiedWithNewTitle)
    }

    @Test
    fun `copy allows changing artworkUri to null`() {
        val uriString = "content://media/external/audio/albumart/1"
        val mockUriInstance = mockUri(uriString)
        every { Uri.parse(uriString) } returns mockUriInstance

        val original = Album(albumId = 1L, title = "Title", artist = "Artist", artistId = 1, artworkUri = mockUriInstance, numberOfSongs = 5)
        val copied = original.copy(artworkUri = null)

        assertNull(copied.artworkUri)
        assertEquals(original.albumId, copied.albumId)
    }

    @Test
    fun `copy allows changing artworkUri from null`() {
        val newUriString = "content://media/external/audio/albumart/2"
        val newMockUriInstance = mockUri(newUriString)
        every { Uri.parse(newUriString) } returns newMockUriInstance

        val original = Album(albumId = 1L, title = "Title", artist = "Artist", artistId = 1, artworkUri = null, numberOfSongs = 5)
        val copied = original.copy(artworkUri = newMockUriInstance)

        assertEquals(newMockUriInstance, copied.artworkUri)
        assertEquals(original.albumId, copied.albumId)
    }

    @Test
    fun `toString contains property values`() {
        val uriString = "content://media/external/audio/albumart/7"
        val mockUriInstance = mockUri(uriString) // mockUri now stubs toString()
        every { Uri.parse(uriString) } returns mockUriInstance

        val album = Album(albumId = 7L, title = "Album Seven", artist = "Artist Seven", artistId = 77, artworkUri = mockUriInstance, numberOfSongs = 17)
        val stringRepresentation = album.toString()

        assertTrue(stringRepresentation.contains("albumId=7"))
        assertTrue(stringRepresentation.contains("title=Album Seven"))
        // ... other assertions
        assertTrue(stringRepresentation.contains("artworkUri=$uriString")) // Check against the string value
    }

    @Test
    fun `toString handles null artworkUri`() {
        val album = Album(albumId = 8L, title = "Album Eight", artist = "Artist Eight", artistId = 88, artworkUri = null, numberOfSongs = 18)
        val stringRepresentation = album.toString()

        assertTrue(stringRepresentation.contains("artworkUri=null"))
    }
}