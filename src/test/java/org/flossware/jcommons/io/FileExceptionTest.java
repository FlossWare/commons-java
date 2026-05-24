package org.flossware.jcommons.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileExceptionTest {

    @Test
    void testDefaultConstructor() {
        FileException ex = new FileException();
        assertNull(ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testMessageConstructor() {
        FileException ex = new FileException("Test message");
        assertEquals("Test message", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testCauseConstructor() {
        IOException cause = new IOException("cause");
        FileException ex = new FileException(cause);
        assertEquals(cause, ex.getCause());
        assertTrue(ex.getMessage().contains("IOException"));
    }

    @Test
    void testMessageAndCauseConstructor() {
        IOException cause = new IOException("cause");
        FileException ex = new FileException("message", cause);
        assertEquals("message", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testIsRuntimeException() {
        FileException ex = new FileException();
        assertTrue(ex instanceof RuntimeException);
    }

    @Test
    void testSerializable() {
        FileException ex = new FileException("test");
        assertTrue(ex instanceof java.io.Serializable);
    }
}
