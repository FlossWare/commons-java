package org.flossware.jcommons.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SoapExceptionTest {

    @Test
    void testDefaultConstructor() {
        SoapException ex = new SoapException();
        assertNull(ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testMessageConstructor() {
        SoapException ex = new SoapException("Test message");
        assertEquals("Test message", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testCauseConstructor() {
        IOException cause = new IOException("cause");
        SoapException ex = new SoapException(cause);
        assertEquals(cause, ex.getCause());
        assertTrue(ex.getMessage().contains("IOException"));
    }

    @Test
    void testMessageAndCauseConstructor() {
        IOException cause = new IOException("cause");
        SoapException ex = new SoapException("message", cause);
        assertEquals("message", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    void testIsRuntimeException() {
        SoapException ex = new SoapException();
        assertTrue(ex instanceof RuntimeException);
    }

    @Test
    void testSerializable() {
        SoapException ex = new SoapException("test");
        assertTrue(ex instanceof java.io.Serializable);
    }
}
