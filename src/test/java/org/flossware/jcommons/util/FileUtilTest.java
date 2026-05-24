package org.flossware.jcommons.util;

import org.flossware.jcommons.io.FileException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileUtilTest {

    @TempDir
    Path tempDir;

    @Test
    void testGetFileInputStream_withValidFile() throws IOException {
        Path testFile = tempDir.resolve("test.txt");
        Files.writeString(testFile, "test content");

        FileInputStream fis = FileUtil.getFileInputStream(testFile.toFile());
        assertNotNull(fis);
        fis.close();
    }

    @Test
    void testGetFileInputStream_withNonExistentFile() {
        File nonExistent = new File(tempDir.toFile(), "nonexistent.txt");
        assertThrows(FileException.class, () ->
            FileUtil.getFileInputStream(nonExistent));
    }

    @Test
    void testGetFileInputStream_withNullFile() {
        assertThrows(NullPointerException.class, () ->
            FileUtil.getFileInputStream((File) null));
    }

    @Test
    void testGetFileInputStream_withFileName() throws IOException {
        Path testFile = tempDir.resolve("test2.txt");
        Files.writeString(testFile, "test content");

        FileInputStream fis = FileUtil.getFileInputStream(testFile.toString());
        assertNotNull(fis);
        fis.close();
    }

    @Test
    void testGetFileInputStream_withNullFileName() {
        assertThrows(IllegalArgumentException.class, () ->
            FileUtil.getFileInputStream((String) null));
    }

    @Test
    void testGetFileInputStream_withEmptyFileName() {
        assertThrows(IllegalArgumentException.class, () ->
            FileUtil.getFileInputStream(""));
    }

    @Test
    void testEnsureFileExists_withValidFile() throws IOException {
        Path testFile = tempDir.resolve("exists.txt");
        Files.writeString(testFile, "exists");

        File result = FileUtil.ensureFileExists(testFile.toFile());
        assertEquals(testFile.toFile(), result);
    }

    @Test
    void testEnsureFileExists_withNonExistentFile() {
        File nonExistent = new File(tempDir.toFile(), "nonexistent.txt");
        assertThrows(IllegalArgumentException.class, () ->
            FileUtil.ensureFileExists(nonExistent));
    }

    @Test
    void testEnsureFileExists_withNullFile() {
        assertThrows(NullPointerException.class, () ->
            FileUtil.ensureFileExists((File) null));
    }

    @Test
    void testEnsureFileExists_withFileName() throws IOException {
        Path testFile = tempDir.resolve("exists2.txt");
        Files.writeString(testFile, "exists");

        File result = FileUtil.ensureFileExists(testFile.toString());
        assertEquals(testFile.toFile(), result);
    }

    @Test
    void testEnsureFileExists_withNullFileName() {
        assertThrows(IllegalArgumentException.class, () ->
            FileUtil.ensureFileExists((String) null));
    }

    @Test
    void testEnsureFileExists_withEmptyFileName() {
        assertThrows(IllegalArgumentException.class, () ->
            FileUtil.ensureFileExists(""));
    }

    // ========== NIO.2 Path-based method tests ==========

    @Test
    void testNewInputStream_withValidPath() throws IOException {
        Path testFile = tempDir.resolve("nio_test.txt");
        Files.writeString(testFile, "NIO.2 test content");

        try (InputStream is = FileUtil.newInputStream(testFile)) {
            assertNotNull(is);
            assertTrue(is.available() > 0);
        }
    }

    @Test
    void testNewInputStream_withNonExistentPath() {
        Path nonExistent = tempDir.resolve("nonexistent_nio.txt");
        assertThrows(FileException.class, () ->
            FileUtil.newInputStream(nonExistent));
    }

    @Test
    void testNewInputStream_withNullPath() {
        assertThrows(NullPointerException.class, () ->
            FileUtil.newInputStream((Path) null));
    }

    @Test
    void testNewInputStream_withPathString() throws IOException {
        Path testFile = tempDir.resolve("nio_test2.txt");
        Files.writeString(testFile, "NIO.2 test content");

        try (InputStream is = FileUtil.newInputStream(testFile.toString())) {
            assertNotNull(is);
            assertTrue(is.available() > 0);
        }
    }

    @Test
    void testNewInputStream_withNullPathString() {
        assertThrows(IllegalArgumentException.class, () ->
            FileUtil.newInputStream((String) null));
    }

    @Test
    void testNewInputStream_withEmptyPathString() {
        assertThrows(IllegalArgumentException.class, () ->
            FileUtil.newInputStream(""));
    }

    @Test
    void testRequireExists_withValidPath() throws IOException {
        Path testFile = tempDir.resolve("nio_exists.txt");
        Files.writeString(testFile, "exists");

        Path result = FileUtil.requireExists(testFile);
        assertSame(testFile, result);
    }

    @Test
    void testRequireExists_withNonExistentPath() {
        Path nonExistent = tempDir.resolve("nonexistent_nio.txt");
        assertThrows(IllegalArgumentException.class, () ->
            FileUtil.requireExists(nonExistent));
    }

    @Test
    void testRequireExists_withNullPath() {
        assertThrows(NullPointerException.class, () ->
            FileUtil.requireExists((Path) null));
    }

    @Test
    void testRequireExists_withPathString() throws IOException {
        Path testFile = tempDir.resolve("nio_exists2.txt");
        Files.writeString(testFile, "exists");

        Path result = FileUtil.requireExists(testFile.toString());
        assertEquals(testFile, result);
    }

    @Test
    void testRequireExists_withNullPathString() {
        assertThrows(IllegalArgumentException.class, () ->
            FileUtil.requireExists((String) null));
    }

    @Test
    void testRequireExists_withEmptyPathString() {
        assertThrows(IllegalArgumentException.class, () ->
            FileUtil.requireExists(""));
    }

    @Test
    void testRequireRegularFile_withValidFile() throws IOException {
        Path testFile = tempDir.resolve("regular_file.txt");
        Files.writeString(testFile, "regular file");

        Path result = FileUtil.requireRegularFile(testFile);
        assertSame(testFile, result);
    }

    @Test
    void testRequireRegularFile_withDirectory() {
        assertThrows(IllegalArgumentException.class, () ->
            FileUtil.requireRegularFile(tempDir));
    }

    @Test
    void testRequireRegularFile_withNonExistent() {
        Path nonExistent = tempDir.resolve("nonexistent.txt");
        assertThrows(IllegalArgumentException.class, () ->
            FileUtil.requireRegularFile(nonExistent));
    }

    @Test
    void testRequireDirectory_withValidDirectory() {
        Path result = FileUtil.requireDirectory(tempDir);
        assertSame(tempDir, result);
    }

    @Test
    void testRequireDirectory_withFile() throws IOException {
        Path testFile = tempDir.resolve("file_not_dir.txt");
        Files.writeString(testFile, "not a directory");

        assertThrows(IllegalArgumentException.class, () ->
            FileUtil.requireDirectory(testFile));
    }

    @Test
    void testRequireDirectory_withNonExistent() {
        Path nonExistent = tempDir.resolve("nonexistent_dir");
        assertThrows(IllegalArgumentException.class, () ->
            FileUtil.requireDirectory(nonExistent));
    }

    @Test
    void testRequireReadable_withReadableFile() throws IOException {
        Path testFile = tempDir.resolve("readable.txt");
        Files.writeString(testFile, "readable content");

        Path result = FileUtil.requireReadable(testFile);
        assertSame(testFile, result);
    }

    @Test
    void testRequireReadable_withNonExistent() {
        Path nonExistent = tempDir.resolve("nonexistent.txt");
        assertThrows(IllegalArgumentException.class, () ->
            FileUtil.requireReadable(nonExistent));
    }

    @Test
    void testRequireWritable_withWritableFile() throws IOException {
        Path testFile = tempDir.resolve("writable.txt");
        Files.writeString(testFile, "writable content");

        Path result = FileUtil.requireWritable(testFile);
        assertSame(testFile, result);
    }

    @Test
    void testRequireWritable_withNonExistent() {
        Path nonExistent = tempDir.resolve("nonexistent.txt");
        assertThrows(IllegalArgumentException.class, () ->
            FileUtil.requireWritable(nonExistent));
    }

    @Test
    void testRequireReadable_withNonReadableFile() throws IOException {
        Path testFile = tempDir.resolve("nonreadable.txt");
        Files.writeString(testFile, "content");
        File file = testFile.toFile();

        // Make file non-readable
        file.setReadable(false);
        try {
            assertThrows(IllegalArgumentException.class, () ->
                FileUtil.requireReadable(testFile));
        } finally {
            // Restore permissions for cleanup
            file.setReadable(true);
        }
    }

    @Test
    void testRequireWritable_withNonWritableFile() throws IOException {
        Path testFile = tempDir.resolve("nonwritable.txt");
        Files.writeString(testFile, "content");
        File file = testFile.toFile();

        // Make file non-writable
        file.setWritable(false);
        try {
            assertThrows(IllegalArgumentException.class, () ->
                FileUtil.requireWritable(testFile));
        } finally {
            // Restore permissions for cleanup
            file.setWritable(true);
        }
    }
}
