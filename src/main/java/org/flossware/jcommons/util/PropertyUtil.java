/*
 * Copyright (C) 2017 Scot P. Floess
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
package org.flossware.jcommons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.flossware.jcommons.io.FileException;
import org.flossware.jcommons.io.JCommonsIOException;

/**
 * Properties utility class.
 *
 * @author Scot P. Floess
 */
public final class PropertyUtil {
    /**
     * Our logger.
     */
    private static final Logger LOGGER = Logger.getLogger(PropertyUtil.class.getName());


    /**
     * Return the logger.
     *
     * @return our logger.
     */
    private static Logger getLogger() {
        return LOGGER;
    }

    /**
     * Default constructor not allowed.
     */
    private PropertyUtil() {
        throw new AssertionError("Utility class - do not instantiate");
    }


    static Properties populateFromInputStream(final Properties retVal, final InputStream inputStream, final boolean closeStream) {
        try {
            retVal.load(Objects.requireNonNull(inputStream, "Must provide an input stream!"));

            return retVal;
        } catch (final IOException ioException) {
            LoggerUtil.log(getLogger(), Level.WARNING, "Trouble reading input stream!", ioException);

            throw new JCommonsIOException(ioException);
        } finally {
            if (closeStream) {
                IOUtils.close(inputStream);
            }
        }
    }

    static Properties populateFromReader(final Properties retVal, final Reader reader, final boolean closeReader) {
        try {
            retVal.load(Objects.requireNonNull(reader, "Must provide a reader!"));

            return retVal;
        } catch (final IOException ioException) {
            LoggerUtil.log(getLogger(), Level.WARNING, "Trouble reading from reader!", ioException);

            throw new JCommonsIOException(ioException);
        } finally {
            if (closeReader) {
                IOUtils.close(reader);
            }
        }
    }

    /**
     * Load properties from an input stream.
     *
     * @param inputStream the input stream to read from
     * @param closeStream whether to close the stream after reading
     * @return Properties object loaded from the stream
     * @throws JCommonsIOException if reading fails
     */
    public static Properties fromInputStream(final InputStream inputStream, final boolean closeStream) {
        return populateFromInputStream(new Properties(), inputStream, closeStream);
    }

    /**
     * Load properties from an input stream without closing it.
     *
     * @param inputStream the input stream to read from
     * @return Properties object loaded from the stream
     * @throws JCommonsIOException if reading fails
     */
    public static Properties fromInputStream(final InputStream inputStream) {
        return fromInputStream(inputStream, false);
    }

    /**
     * Load properties from a classpath resource.
     *
     * @param resource the resource path to load from
     * @return Properties object loaded from the resource
     * @throws IllegalArgumentException if resource parameter is null
     * @throws JCommonsIOException if resource cannot be found or read
     */
    public static Properties fromResource(final String resource) {
        Objects.requireNonNull(resource, "Resource path must not be null");

        InputStream inputStream = PropertyUtil.class.getClassLoader().getResourceAsStream(resource);
        if (inputStream == null) {
            throw new JCommonsIOException("Resource not found on classpath: " + resource);
        }

        return fromInputStream(inputStream, true);
    }

    /**
     * Load properties from a reader.
     *
     * @param reader the reader to read from
     * @param closeReader whether to close the reader after reading
     * @return Properties object loaded from the reader
     * @throws JCommonsIOException if reading fails
     */
    public static Properties fromReader(final Reader reader, final boolean closeReader) {
        return populateFromReader(new Properties(), reader, closeReader);
    }

    /**
     * Load properties from a reader without closing it.
     *
     * @param reader the reader to read from
     * @return Properties object loaded from the reader
     * @throws JCommonsIOException if reading fails
     */
    public static Properties fromReader(final Reader reader) {
        return fromReader(reader, false);
    }

    /**
     * Load properties from a file.
     *
     * @param file the file to load properties from
     * @return Properties object loaded from the file
     * @throws FileException if the file cannot be read
     */
    public static Properties fromFile(final File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            return populateFromInputStream(new Properties(), fis, false);
        } catch (final FileNotFoundException fnfe) {
            throw new FileException(fnfe);
        } catch (final IOException ioe) {
            throw new FileException(ioe);
        }
    }

    /**
     * Load properties from a file specified by path.
     *
     * @param filename the path to the properties file
     * @return Properties object loaded from the file
     * @throws FileException if the file cannot be read
     */
    public static Properties fromFile(final String filename) {
        return fromFile(new File(filename));
    }
}
