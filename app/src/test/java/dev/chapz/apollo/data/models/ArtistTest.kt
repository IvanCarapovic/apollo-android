package dev.chapz.apollo.data.models // Adjust to your package structure

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

class ArtistTest {

    // Helper to create a mocked Uri instance
    private fun mockUri(uriString: String): Uri {
        val mockedUri = mockk<Uri>()
        every { mockedUri.toString() } returns uriString
        // Add other common Uri behaviors if your Artist class or tests might use them
        return mockedUri
    }

    @Before
    fun setUp() {
        // Mock the static Uri.parse method if your Artist class or tests might use it.
        // If Artist only stores a Uri but doesn't parse strings to Uri itself,
        // this might not be strictly necessary for ArtistTest but is good practice
        // if any related utility functions you test elsewhere might use it.
        mockkStatic(Uri::class)
    }

    @After
    fun tearDown() {
        unmockkStatic(Uri::class)
    }

    // --- Property Getter Tests ---

    @Test
    fun `id returns correct value`() {
        val artist = Artist(123, "Artist Name", null, 5, 50)
        assertEquals(123, artist.id)
    }

    @Test
    fun `name returns correct value when not null`() {
        val artist = Artist(1, "Awesome Artist", null, 2, 20)
        assertEquals("Awesome Artist", artist.artist)
    }

    @Test
    fun `artworkUri returns correct value when not null`() {
        val artUriString = "content://media/external/audio/albumart/artist_art"
        val mockArtUri = mockUri(artUriString)
        // If your Artist class or related logic ever calls Uri.parse for this:
        // every { Uri.parse(artUriString) } returns mockArtUri

        val artist = Artist(1, "Artist X", mockArtUri, 3, 30)
        assertEquals(mockArtUri, artist.artworkUri)
    }

    @Test
    fun `artworkUri returns null when null`() {
        val artist = Artist(1, "Artist Y", null, 3, 30)
        assertNull(artist.artworkUri)
    }

    @Test
    fun `numberOfAlbums returns correct value`() {
        val artist = Artist(1, "Artist Z", null, 7, 70)
        assertEquals(7, artist.numberOfAlbums)
    }

    @Test
    fun `numberOfTracks returns correct value`() {
        val artist = Artist(1, "Artist A", null, 4, 45)
        assertEquals(45, artist.numberOfTracks)
    }

    // --- equals() and hashCode() Tests ---

    @Test
    fun `equals returns true for identical instances`() {
        val artUri = mockUri("uri:art1")
        val artist1 = Artist(1, "Artist Name", artUri, 2, 15)
        val artist2 = Artist(1, "Artist Name", artUri, 2, 15)
        assertEquals(artist1, artist2)
    }

    @Test
    fun `equals returns false for different id`() {
        val artUri = mockUri("uri:art1")
        val artist1 = Artist(1, "Artist Name", artUri, 2, 15)
        val artist2 = Artist(2, "Artist Name", artUri, 2, 15) // Different ID
        assertNotEquals(artist1, artist2)
    }

    @Test
    fun `equals returns false for different name`() {
        val artUri = mockUri("uri:art1")
        val artist1 = Artist(1, "Artist One", artUri, 2, 15)
        val artist2 = Artist(1, "Artist Two", artUri, 2, 15) // Different name
        assertNotEquals(artist1, artist2)
    }

    @Test
    fun `equals returns false for different artworkUri (null vs non-null)`() {
        val artUri = mockUri("uri:art1")
        val artist1 = Artist(1, "Artist Name", null, 2, 15)
        val artist2 = Artist(1, "Artist Name", artUri, 2, 15) // Different artworkUri
        assertNotEquals(artist1, artist2)
    }

    @Test
    fun `equals returns false for different artworkUri (different Uris)`() {
        val artUri1 = mockUri("uri:art1")
        val artUri2 = mockUri("uri:art2")
        val artist1 = Artist(1, "Artist Name", artUri1, 2, 15)
        val artist2 = Artist(1, "Artist Name", artUri2, 2, 15) // Different artworkUri instances
        assertNotEquals(artist1, artist2)
    }

    @Test
    fun `equals returns false for different numberOfAlbums`() {
        val artist1 = Artist(1, "Artist Name", null, 2, 15)
        val artist2 = Artist(1, "Artist Name", null, 3, 15) // Different numberOfAlbums
        assertNotEquals(artist1, artist2)
    }

    @Test
    fun `equals returns false for different numberOfTracks`() {
        val artist1 = Artist(1, "Artist Name", null, 2, 15)
        val artist2 = Artist(1, "Artist Name", null, 2, 20) // Different numberOfTracks
        assertNotEquals(artist1, artist2)
    }

    @Test
    fun `hashCode is consistent for equal objects`() {
        val artUri = mockUri("uri:art1")
        val artist1 = Artist(1, "Artist Name", artUri, 2, 15)
        val artist2 = Artist(1, "Artist Name", artUri, 2, 15)
        assertEquals(artist1.hashCode(), artist2.hashCode())
    }

    // --- copy() Tests ---

    @Test
    fun `copy creates a new instance with identical properties`() {
        val artUri = mockUri("uri:art_copy")
        val original = Artist(10, "Original Artist", artUri, 5, 55)
        val copied = original.copy()

        assertEquals(original, copied)
        assertNotSame(original, copied) // Ensure they are different instances
    }

    @Test
    fun `copy allows changing a specific property (e_g_ name)`() {
        val original = Artist(10, "Old Name", null, 5, 55)
        val copied = original.copy(artist = "New Name")

        assertEquals("New Name", copied.artist)
        assertEquals(original.id, copied.id) // Check other properties remain
        assertEquals(original.numberOfAlbums, copied.numberOfAlbums)
        assertNotEquals(original, copied)
    }

    @Test
    fun `copy allows changing artworkUri from null to non-null`() {
        val newArtUri = mockUri("uri:new_artist_art")
        val original = Artist(10, "Artist", null, 5, 55)
        val copied = original.copy(artworkUri = newArtUri)

        assertEquals(newArtUri, copied.artworkUri)
        assertNull(original.artworkUri)
    }

    @Test
    fun `copy allows changing numberOfTracks`() {
        val original = Artist(10, "Artist", null, 5, 55)
        val copied = original.copy(numberOfTracks = 60)

        assertEquals(60, copied.numberOfTracks)
        assertEquals(original.numberOfAlbums, copied.numberOfAlbums)
        assertNotEquals(original, copied)
    }

    // --- toString() Tests ---

    @Test
    fun `toString contains property values`() {
        val artUriString = "content://media/external/audio/albumart/artist123"
        val mockArtUri = mockUri(artUriString)
        // every { Uri.parse(artUriString) } returns mockArtUri // If needed

        val artist = Artist(
            id = 42,
            artist = "The Best Artist",
            artworkUri = mockArtUri,
            numberOfAlbums = 10,
            numberOfTracks = 100
        )
        val stringRepresentation = artist.toString()

        assertTrue(stringRepresentation.contains("id=42"))
        assertTrue(stringRepresentation.contains("artist=The Best Artist"))
        assertTrue(stringRepresentation.contains("artworkUri=$artUriString"))
        assertTrue(stringRepresentation.contains("numberOfAlbums=10"))
        assertTrue(stringRepresentation.contains("numberOfTracks=100"))
    }

    @Test
    fun `toString handles null properties correctly`() {
        val artist = Artist(
            id = 1,
            artist = "null",
            artworkUri = null,
            numberOfAlbums = 0,
            numberOfTracks = 0
        )
        val stringRepresentation = artist.toString()

        assertTrue(stringRepresentation.contains("id=1"))
        assertTrue(stringRepresentation.contains("artist=null"))
        assertTrue(stringRepresentation.contains("artworkUri=null"))
        assertTrue(stringRepresentation.contains("numberOfAlbums=0"))
        assertTrue(stringRepresentation.contains("numberOfTracks=0"))
    }
}