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
package org.flossware.commons.util;

import org.flossware.commons.io.FileException;
import org.flossware.commons.io.CommonsIOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PropertyUtilTest {

    @Test
    void testFromInputStream() {
        String propertiesContent = "key1=value1\nkey2=value2\n";
        InputStream inputStream = new ByteArrayInputStream(propertiesContent.getBytes());

        Properties properties = PropertyUtil.fromInputStream(inputStream, false);

        assertEquals("value1", properties.getProperty("key1"));
        assertEquals("value2", properties.getProperty("key2"));
    }

    @Test
    void testFromInputStream_withClose() {
        String propertiesContent = "key1=value1\n";
        InputStream inputStream = new ByteArrayInputStream(propertiesContent.getBytes());

        Properties properties = PropertyUtil.fromInputStream(inputStream, true);

        assertEquals("value1", properties.getProperty("key1"));
    }

    @Test
    void testFromInputStream_withNullInputStream() {
        assertThrows(NullPointerException.class, () ->
            PropertyUtil.fromInputStream(null, false));
    }

    @Test
    void testFromInputStream_withInvalidData() {
        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("Test exception");
            }
        };

        assertThrows(CommonsIOException.class, () ->
            PropertyUtil.fromInputStream(inputStream, false));
    }

    @Test
    void testFromReader() {
        String propertiesContent = "key1=value1\nkey2=value2\n";
        Reader reader = new StringReader(propertiesContent);

        Properties properties = PropertyUtil.fromReader(reader, false);

        assertEquals("value1", properties.getProperty("key1"));
        assertEquals("value2", properties.getProperty("key2"));
    }

    @Test
    void testFromReader_withClose() {
        String propertiesContent = "key1=value1\n";
        Reader reader = new StringReader(propertiesContent);

        Properties properties = PropertyUtil.fromReader(reader, true);

        assertEquals("value1", properties.getProperty("key1"));
    }

    @Test
    void testFromReader_withNullReader() {
        assertThrows(NullPointerException.class, () ->
            PropertyUtil.fromReader(null, false));
    }

    @Test
    void testFromReader_withInvalidData() {
        Reader reader = new Reader() {
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                throw new IOException("Test exception");
            }

            @Override
            public void close() throws IOException {
            }
        };

        assertThrows(CommonsIOException.class, () ->
            PropertyUtil.fromReader(reader, false));
    }

    @Test
    void testFromFile(@TempDir Path tempDir) throws IOException {
        Path tempFile = tempDir.resolve("test.properties");
        String propertiesContent = "key1=value1\nkey2=value2\n";
        java.nio.file.Files.writeString(tempFile, propertiesContent);

        Properties properties = PropertyUtil.fromFile(tempFile.toFile());

        assertEquals("value1", properties.getProperty("key1"));
        assertEquals("value2", properties.getProperty("key2"));
    }

    @Test
    void testFromFile_withStringPath(@TempDir Path tempDir) throws IOException {
        Path tempFile = tempDir.resolve("test.properties");
        String propertiesContent = "key1=value1\n";
        java.nio.file.Files.writeString(tempFile, propertiesContent);

        Properties properties = PropertyUtil.fromFile(tempFile.toString());

        assertEquals("value1", properties.getProperty("key1"));
    }

    @Test
    void testFromFile_withNonExistentFile() {
        assertThrows(FileException.class, () ->
            PropertyUtil.fromFile(new File("/non/existent/file.properties")));
    }

    @Test
    void testFromFile_withNonExistentPath() {
        assertThrows(FileException.class, () ->
            PropertyUtil.fromFile("/non/existent/file.properties"));
    }

    @Test
    void testFromResource() {
        Properties properties = PropertyUtil.fromResource("test.properties");
        assertEquals("test.value", properties.getProperty("test.key"));
    }

    @Test
    void testFromResource_withNonExistentResource() {
        assertThrows(CommonsIOException.class, () ->
            PropertyUtil.fromResource("non-existent-resource.properties"));
    }

    @Test
    void testFromResource_withNull() {
        assertThrows(NullPointerException.class, () ->
            PropertyUtil.fromResource(null));
    }

    @Test
    void testFromInputStream_defaultCloseStream() {
        String propertiesContent = "key1=value1\n";
        InputStream inputStream = new ByteArrayInputStream(propertiesContent.getBytes());

        Properties properties = PropertyUtil.fromInputStream(inputStream);

        assertEquals("value1", properties.getProperty("key1"));
    }

    @Test
    void testFromReader_defaultCloseReader() {
        String propertiesContent = "key1=value1\n";
        Reader reader = new StringReader(propertiesContent);

        Properties properties = PropertyUtil.fromReader(reader);

        assertEquals("value1", properties.getProperty("key1"));
    }

    @Test
    void testPrivateConstructor() throws Exception {
        Constructor<PropertyUtil> constructor = PropertyUtil.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);

        var exception = assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof AssertionError);
        assertEquals("Utility class - do not instantiate", exception.getCause().getMessage());
    }
}
