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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for file operations across FileUtil, PropertyUtil, and IOUtils.
 * Tests realistic workflows that involve multiple utility classes working together.
 */
class FileOperationsIT {

    @TempDir
    Path tempDir;

    @Test
    void testPropertiesFileWorkflow() throws IOException {
        // Create a properties file
        Path propsFile = tempDir.resolve("app.properties");
        Files.writeString(propsFile, "app.name=JCommons\napp.version=1.0\n");

        // Workflow: FileUtil -> InputStream -> PropertyUtil
        FileUtil.requireExists(propsFile);
        FileUtil.requireReadable(propsFile);

        try (InputStream is = FileUtil.newInputStream(propsFile)) {
            Properties props = PropertyUtil.fromInputStream(is, true);

            assertEquals("JCommons", props.getProperty("app.name"));
            assertEquals("1.0", props.getProperty("app.version"));
        }
    }

    @Test
    void testSecureFileAccess() throws IOException {
        // Setup: Create directory structure with files
        Path dataDir = tempDir.resolve("data");
        Path secretsDir = tempDir.resolve("secrets");
        Files.createDirectories(dataDir);
        Files.createDirectories(secretsDir);

        Path allowedFile = dataDir.resolve("allowed.txt");
        Path secretFile = secretsDir.resolve("secret.txt");

        Files.writeString(allowedFile, "public data");
        Files.writeString(secretFile, "secret data");

        // Workflow: Validate path, check permissions, read file
        FileUtil.requireExists(allowedFile);
        FileUtil.requireRegularFile(allowedFile);
        FileUtil.requireReadable(allowedFile);

        try (InputStream is = FileUtil.newInputStream(allowedFile, dataDir)) {
            String content = new String(is.readAllBytes());
            assertEquals("public data", content);
        }

        // Attempt to access file outside base directory should fail
        assertThrows(IllegalArgumentException.class, () ->
            FileUtil.newInputStream(secretFile, dataDir));
    }

    @Test
    void testFileValidationChain() throws IOException {
        Path testFile = tempDir.resolve("validated.txt");
        Files.writeString(testFile, "content");

        // Chain multiple validations
        Path validated = FileUtil.requireExists(testFile);
        validated = FileUtil.requireRegularFile(validated);
        validated = FileUtil.requireReadable(validated);

        assertNotNull(validated);
        assertTrue(Files.exists(validated));
        assertTrue(Files.isRegularFile(validated));
        assertTrue(Files.isReadable(validated));
    }

    @Test
    void testPropertyFileWithIOUtils() throws IOException {
        // Create a temporary properties file
        Path propsFile = tempDir.resolve("config.properties");
        String content = "db.url=jdbc:mysql://localhost\ndb.user=admin\n";
        Files.writeString(propsFile, content);

        // Read using FileUtil and PropertyUtil together
        Properties props = PropertyUtil.fromFile(propsFile.toFile());

        assertEquals("jdbc:mysql://localhost", props.getProperty("db.url"));
        assertEquals("admin", props.getProperty("db.user"));
    }

    @Test
    void testMultipleFileOperations() throws IOException {
        // Create multiple files
        Path file1 = tempDir.resolve("file1.txt");
        Path file2 = tempDir.resolve("file2.txt");
        Path file3 = tempDir.resolve("file3.txt");

        Files.writeString(file1, "File 1");
        Files.writeString(file2, "File 2");
        Files.writeString(file3, "File 3");

        // Validate all files exist and are readable
        FileUtil.requireExists(file1);
        FileUtil.requireExists(file2);
        FileUtil.requireExists(file3);

        // Read all files
        try (InputStream is1 = FileUtil.newInputStream(file1);
             InputStream is2 = FileUtil.newInputStream(file2);
             InputStream is3 = FileUtil.newInputStream(file3)) {

            assertEquals("File 1", new String(is1.readAllBytes()));
            assertEquals("File 2", new String(is2.readAllBytes()));
            assertEquals("File 3", new String(is3.readAllBytes()));
        }
    }
}
