package org.flossware.jcommons.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JCommonsIOExceptionTest {

    @Test
    void testDefaultConstructor() {
        JCommonsIOException ex = new JCommonsIOException();
        assertNull(ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testMessageConstructor() {
        JCommonsIOException ex = new JCommonsIOException("Test message");
        assertEquals("Test message", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testCauseConstructor() {
        IOException cause = new IOException("cause");
        JCommonsIOException ex = new JCommonsIOException(cause);
        assertEquals(cause, ex.getCause());
        assertTrue(ex.getMessage().contains("IOException"));
    }

    @Test
    void testMessageAndCauseConstructor() {
        IOException cause = new IOException("cause");
        JCommonsIOException ex = new JCommonsIOException("message", cause);
        assertEquals("message", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testIsRuntimeException() {
        JCommonsIOException ex = new JCommonsIOException();
        assertTrue(ex instanceof RuntimeException);
    }

    @Test
    void testSerializable() {
        JCommonsIOException ex = new JCommonsIOException("test");
        assertTrue(ex instanceof java.io.Serializable);
    }
}
