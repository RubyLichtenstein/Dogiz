package com.rubylichtenstein.domain.common

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StringExtensionsTest {

    @Test
    fun `capitalize capitalizes first character if lowercase`() {
        assertEquals("Hello", "hello".capitalize())
        assertEquals("WORLD", "WORLD".capitalize())
        assertEquals("1two", "1two".capitalize())
        assertEquals("", "".capitalize())
    }

    @Test
    fun `capitalizeWords capitalizes first character of each word`() {
        assertEquals("Hello World", "hello world".capitalizeWords())
        assertEquals("HELLO WORLD", "HELLO WORLD".capitalizeWords())
        assertEquals("Hello WORLD", "Hello WORLD".capitalizeWords())
        assertEquals("1two Three", "1two three".capitalizeWords())
        assertEquals("", "".capitalizeWords())
    }
}