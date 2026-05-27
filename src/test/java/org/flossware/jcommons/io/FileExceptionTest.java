/*
 * Copyright (C) 2016 Scot P. Floess
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
