package org.flossware.jcommons.util;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.Closeable;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class IOUtilsTest {

    @Test
    void testClose_withValidCloseable() throws IOException {
        Closeable closeable = mock(Closeable.class);
        IOUtils.close(closeable);
        verify(closeable, times(1)).close();
    }

    @Test
    void testClose_withNull() {
        IOUtils.close(null);
    }

    @Test
    void testClose_withIOException() throws IOException {
        Closeable closeable = mock(Closeable.class);
        doThrow(new IOException("Test exception")).when(closeable).close();
        IOUtils.close(closeable);
        verify(closeable, times(1)).close();
    }

    @Test
    void testCloseQuietly_withValidCloseable() throws IOException {
        Closeable closeable = mock(Closeable.class);
        IOUtils.closeQuietly(closeable);
        verify(closeable, times(1)).close();
    }

    @Test
    void testCloseQuietly_withNull() {
        IOUtils.closeQuietly(null);
    }

    @Test
    void testCloseQuietly_withIOException() throws IOException {
        Closeable closeable = mock(Closeable.class);
        doThrow(new IOException("Test exception")).when(closeable).close();
        IOUtils.closeQuietly(closeable);
        verify(closeable, times(1)).close();
    }

    @Test
    void testPrivateConstructor() throws Exception {
        java.lang.reflect.Constructor<IOUtils> constructor = IOUtils.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);

        var exception = assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof AssertionError);
        assertEquals("Utility class - do not instantiate", exception.getCause().getMessage());
    }
}
