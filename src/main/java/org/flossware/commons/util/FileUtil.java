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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.flossware.commons.io.FileException;

/**
 * A file utility class supporting both legacy java.io.File and modern java.nio.file.Path APIs.
 *
 * @author Scot P. Floess
 */
public final class FileUtil {
    /**
     * Our logger.
     */
    private static final Logger LOGGER = Logger.getLogger(FileUtil.class.getName());

    /**
     * Default constructor not allowed.
     */
    private FileUtil() {
        throw new AssertionError("Utility class - do not instantiate");
    }

    /**
     * Return the logger.
     *
     * @return our logger.
     */
    private static Logger getLogger() {
        return LOGGER;
    }

    // ========== Path Traversal Security ==========

    /**
     * Validates that a path does not escape a base directory using path traversal.
     * Resolves symbolic links and normalizes paths to detect traversal attempts.
     *
     * @param path the path to validate
     * @param baseDirectory the base directory that path must be within
     * @return the normalized, real path if validation succeeds
     * @throws IllegalArgumentException if path is null, baseDirectory is null,
     *                                  or path attempts to escape baseDirectory
     * @throws FileException if there is an I/O error resolving paths
     */
    public static Path validatePathTraversal(final Path path, final Path baseDirectory) {
        Objects.requireNonNull(path, "Path must not be null");
        Objects.requireNonNull(baseDirectory, "Base directory must not be null");

        try {
            // Resolve to real path (follows symlinks, normalizes ..)
            final Path realPath = path.toRealPath();
            final Path realBase = baseDirectory.toRealPath();

            // Check if path is within base directory
            if (!realPath.startsWith(realBase)) {
                throw new IllegalArgumentException(
                    "Path traversal detected: " + path + " escapes base directory " + baseDirectory
                );
            }

            return realPath;
        } catch (final IOException ioException) {
            LoggerUtil.log(getLogger(), Level.WARNING, ioException,
                "Cannot validate path [{0}] against base [{1}]", path, baseDirectory);
            throw new FileException(ioException);
        }
    }

    /**
     * Validates that a path does not contain suspicious traversal patterns.
     * This is a fast check that doesn't require filesystem access.
     *
     * @param path the path to validate
     * @throws IllegalArgumentException if path contains traversal patterns
     */
    public static void validateNoTraversalPatterns(final Path path) {
        Objects.requireNonNull(path, "Path must not be null");

        final String pathString = path.toString();

        // Check for common traversal patterns
        if (pathString.contains("..") ||
            pathString.contains("./") ||
            pathString.contains(".\\")) {
            throw new IllegalArgumentException(
                "Path contains suspicious traversal patterns: " + path
            );
        }
    }

    // ========== Modern NIO.2 Path-based methods ==========

    /**
     * Return an input stream for the given path using NIO.2.
     *
     * @param path the path for which we desire an input stream
     * @return an input stream for reading from the path
     * @throws IllegalArgumentException if path is null
     * @throws FileException if there is any problem opening the input stream
     */
    public static InputStream newInputStream(final Path path) {
        try {
            return Files.newInputStream(Objects.requireNonNull(path, "Path must not be null"));
        } catch (final IOException ioException) {
            LoggerUtil.log(getLogger(), Level.WARNING, ioException, "Cannot open input stream for path [{0}]", path);
            throw new FileException(ioException);
        }
    }

    /**
     * Return an input stream for the given path with path traversal validation.
     *
     * @param path the path for which we desire an input stream
     * @param baseDirectory the base directory that path must be within
     * @return an input stream for reading from the path
     * @throws IllegalArgumentException if path is null, baseDirectory is null,
     *                                  or path attempts to escape baseDirectory
     * @throws FileException if there is any problem opening the input stream
     */
    public static InputStream newInputStream(final Path path, final Path baseDirectory) {
        final Path validatedPath = validatePathTraversal(path, baseDirectory);
        return newInputStream(validatedPath);
    }

    /**
     * Return an input stream for the given path string using NIO.2.
     *
     * @param pathString the path string for which we desire an input stream
     * @return an input stream for reading from the path
     * @throws IllegalArgumentException if pathString is null or empty
     * @throws FileException if there is any problem opening the input stream
     */
    public static InputStream newInputStream(final String pathString) {
        return newInputStream(Paths.get(StringUtil.requireNonBlank(pathString, "Path string must not be null or empty")));
    }

    /**
     * Ensures a path exists.
     *
     * @param path the path to verify exists
     * @return the path if it exists
     * @throws IllegalArgumentException if path is null or does not exist
     */
    public static Path requireExists(final Path path) {
        Objects.requireNonNull(path, "Path must not be null");

        if (!Files.exists(path)) {
            throw new IllegalArgumentException(path + " does not exist!");
        }

        return path;
    }

    /**
     * Ensures a path exists.
     *
     * @param pathString the path string to verify exists
     * @return the Path if it exists
     * @throws IllegalArgumentException if pathString is null, empty, or does not exist
     */
    public static Path requireExists(final String pathString) {
        return requireExists(Paths.get(StringUtil.requireNonBlank(pathString, "Path string must not be null or empty")));
    }

    /**
     * Ensures a path represents a regular file (not a directory).
     *
     * @param path the path to verify is a regular file
     * @return the path if it is a regular file
     * @throws IllegalArgumentException if path is null, does not exist, or is not a regular file
     */
    public static Path requireRegularFile(final Path path) {
        requireExists(path);

        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException(path + " is not a regular file!");
        }

        return path;
    }

    /**
     * Ensures a path represents a directory.
     *
     * @param path the path to verify is a directory
     * @return the path if it is a directory
     * @throws IllegalArgumentException if path is null, does not exist, or is not a directory
     */
    public static Path requireDirectory(final Path path) {
        requireExists(path);

        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException(path + " is not a directory!");
        }

        return path;
    }

    /**
     * Ensures a path is readable.
     *
     * @param path the path to verify is readable
     * @return the path if it is readable
     * @throws IllegalArgumentException if path is null, does not exist, or is not readable
     */
    public static Path requireReadable(final Path path) {
        requireExists(path);

        if (!Files.isReadable(path)) {
            throw new IllegalArgumentException(path + " is not readable!");
        }

        return path;
    }

    /**
     * Ensures a path is writable.
     *
     * @param path the path to verify is writable
     * @return the path if it is writable
     * @throws IllegalArgumentException if path is null, does not exist, or is not writable
     */
    public static Path requireWritable(final Path path) {
        requireExists(path);

        if (!Files.isWritable(path)) {
            throw new IllegalArgumentException(path + " is not writable!");
        }

        return path;
    }

    // ========== Legacy File-based methods (deprecated) ==========

    /**
     * Return a file input stream for file.
     *
     * @param file the file for whom we desire a file input stream
     * @return a file input stream
     * @throws IllegalArgumentException if file is null
     * @throws FileException if there is any problem creating the file input stream
     * @deprecated Use {@link #newInputStream(Path)} instead for modern NIO.2 API
     */
    @Deprecated(since = "1.22", forRemoval = true)
    public static FileInputStream getFileInputStream(final File file) {
        try {
            return new FileInputStream(Objects.requireNonNull(file, "File must not be null"));
        } catch (final FileNotFoundException fileNotFoundException) {
            LoggerUtil.log(getLogger(), Level.WARNING, fileNotFoundException, "File [{0}] not found", file);
            throw new FileException(fileNotFoundException);
        }
    }

    /**
     * Return a file input stream for file.
     *
     * @param fileName the name of the file for whom we desire a file input stream
     * @return a file input stream
     * @throws IllegalArgumentException if fileName is null or empty
     * @throws FileException if there is any problem creating the file input stream
     * @deprecated Use {@link #newInputStream(String)} instead for modern NIO.2 API
     */
    @Deprecated(since = "1.22", forRemoval = true)
    public static FileInputStream getFileInputStream(final String fileName) {
        return getFileInputStream(new File(StringUtil.requireNonBlank(fileName, "File name must not be null or empty")));
    }

    /**
     * Ensures a file exists.
     *
     * @param file the file to determine if it exists
     * @return file if it exists
     * @throws IllegalArgumentException if file does not exist
     * @deprecated Use {@link #requireExists(Path)} instead for modern NIO.2 API
     */
    @Deprecated(since = "1.22", forRemoval = true)
    public static File ensureFileExists(final File file) {
        Objects.requireNonNull(file, "File must not be null");

        if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist!");
        }

        return file;
    }

    /**
     * Ensures a file exists.
     *
     * @param file the file to determine if it exists
     * @return file if it exists
     * @throws IllegalArgumentException if file does not exist
     * @deprecated Use {@link #requireExists(String)} instead for modern NIO.2 API
     */
    @Deprecated(since = "1.22", forRemoval = true)
    public static File ensureFileExists(final String file) {
        return ensureFileExists(new File(StringUtil.requireNonBlank(file, "File name must not be null or empty")));
    }
}
