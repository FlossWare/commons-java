package org.flossware.jcommons.util;

import org.flossware.jcommons.io.FileException;
import org.flossware.jcommons.io.JCommonsIOException;
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
        assertThrows(JCommonsIOException.class, () ->
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

        assertThrows(JCommonsIOException.class, () ->
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
        assertThrows(JCommonsIOException.class, () ->
            PropertyUtil.fromReader(null, false));
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
        assertThrows(JCommonsIOException.class, () ->
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
        constructor.newInstance();
    }

    @Test
    void testPrivateGetLogger() throws Exception {
        java.lang.reflect.Method method = PropertyUtil.class.getDeclaredMethod("getLogger");
        method.setAccessible(true);
        Object logger = method.invoke(null);
        assertNotNull(logger);
    }
}
