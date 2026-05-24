package org.flossware.jcommons.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UrlExceptionTest {

    @Test
    void testDefaultConstructor() {
        UrlException ex = new UrlException();
        assertNull(ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testMessageConstructor() {
        UrlException ex = new UrlException("Test message");
        assertEquals("Test message", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testCauseConstructor() {
        IOException cause = new IOException("cause");
        UrlException ex = new UrlException(cause);
        assertEquals(cause, ex.getCause());
        assertTrue(ex.getMessage().contains("IOException"));
    }

    @Test
    void testMessageAndCauseConstructor() {
        IOException cause = new IOException("cause");
        UrlException ex = new UrlException("message", cause);
        assertEquals("message", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testIsRuntimeException() {
        UrlException ex = new UrlException();
        assertTrue(ex instanceof RuntimeException);
    }

    @Test
    void testSerializable() {
        UrlException ex = new UrlException("test");
        assertTrue(ex instanceof java.io.Serializable);
    }
}
