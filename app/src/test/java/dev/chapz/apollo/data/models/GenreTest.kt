package dev.chapz.apollo.data.models

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class GenreTest {

    @Test
    fun `genreId returns correct value`() {
        val genre = Genre(1L, "Rock", 42)
        assertEquals(1L, genre.id)
    }

    @Test
    fun `name returns correct value when not null`() {
        val genre = Genre(2L, "Jazz", 10)
        assertEquals("Jazz", genre.genre)
    }

    @Test
    fun `name returns null when null`() {
        val genre = Genre(3L, null, 5)
        assertNull(genre.genre)
    }

    @Test
    fun `numberOfSongs returns correct value`() {
        val genre = Genre(4L, "Pop", 100)
        assertEquals(100, genre.numberOfSongs)
    }

    // --- equals() and hashCode() Tests ---

    @Test
    fun `equals returns true for identical instances`() {
        val genre1 = Genre(5L, "Classical", 20)
        val genre2 = Genre(5L, "Classical", 20)
        assertEquals(genre1, genre2)
    }

    @Test
    fun `equals returns false for different genreId`() {
        val genre1 = Genre(6L, "Hip-Hop", 15)
        val genre2 = Genre(7L, "Hip-Hop", 15)
        assertNotEquals(genre1, genre2)
    }

    @Test
    fun `equals returns false for different name`() {
        val genre1 = Genre(8L, "EDM", 30)
        val genre2 = Genre(8L, "Dance", 30)
        assertNotEquals(genre1, genre2)
    }

    @Test
    fun `equals returns false for different numberOfSongs`() {
        val genre1 = Genre(9L, "Country", 12)
        val genre2 = Genre(9L, "Country", 13)
        assertNotEquals(genre1, genre2)
    }

    @Test
    fun `hashCode is consistent for equal objects`() {
        val genre1 = Genre(10L, "Soul", 8)
        val genre2 = Genre(10L, "Soul", 8)
        assertEquals(genre1.hashCode(), genre2.hashCode())
    }

    // --- copy() Tests ---

    @Test
    fun `copy creates a new instance with identical properties`() {
        val original = Genre(11L, "Funk", 22)
        val copied = original.copy()
        assertEquals(original, copied)
        assertNotSame(original, copied)
    }

    @Test
    fun `copy allows changing a specific property (e_g_ name)`() {
        val original = Genre(12L, "Old Name", 5)
        val copied = original.copy(genre = "New Name")
        assertEquals("New Name", copied.genre)
        assertEquals(original.id, copied.id)
        assertEquals(original.numberOfSongs, copied.numberOfSongs)
        assertNotEquals(original, copied)
    }

    // --- toString() Tests ---

    @Test
    fun `toString contains property values`() {
        val genre = Genre(13L, "Reggae", 17)
        val stringRepresentation = genre.toString()
        assertTrue(stringRepresentation.contains("genreId=13"))
        assertTrue(stringRepresentation.contains("name=Reggae"))
        assertTrue(stringRepresentation.contains("numberOfSongs=17"))
    }

    @Test
    fun `toString handles null name correctly`() {
        val genre = Genre(14L, null, 0)
        val stringRepresentation = genre.toString()
        assertTrue(stringRepresentation.contains("genreId=14"))
        assertTrue(stringRepresentation.contains("name=null"))
        assertTrue(stringRepresentation.contains("numberOfSongs=0"))
    }
}

