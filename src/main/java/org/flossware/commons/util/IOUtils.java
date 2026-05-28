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

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * IO utility class.
 *
 * @author Scot P. Floess
 */
public final class IOUtils {
    /**
     * Our logger.
     */
    private static final Logger LOGGER = Logger.getLogger(IOUtils.class.getName());


    /**
     * Return the logger.
     *
     * @return the logger.
     */
    private static Logger getLogger() {
        return LOGGER;
    }

    /**
     * Default constructor not allowed.
     */
    private IOUtils() {
        throw new AssertionError("Utility class - do not instantiate");
    }

    /**
     * Attempts to close a closeable. If any trouble arises closing, an error is logged.
     *
     * @param closeable the thing to close.
     */
    public static void close(final Closeable closeable) {
        if (null == closeable) {
            return;
        }

        try {
            closeable.close();
        } catch (final IOException ioException) {
            getLogger().log(Level.SEVERE, "Trouble closing " + closeable.getClass().getSimpleName(), ioException);
        }
    }

    /**
     * Attempts to close a closeable quietly. If any trouble arises closing, no error is logged.
     * This is useful for cleanup in finally blocks where you don't want exceptions to mask
     * the original exception.
     *
     * @param closeable the thing to close.
     */
    public static void closeQuietly(final Closeable closeable) {
        if (null == closeable) {
            return;
        }

        try {
            closeable.close();
        } catch (final IOException ioException) { // NOPMD - EmptyCatchBlock is intentional for quiet close
            // Silently ignore - this is a "quiet" close
        }
    }
}
