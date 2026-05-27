/*
 * Copyright (C) 2014 Scot P. Floess
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
package org.flossware.jcommons;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.flossware.jcommons.util.LoggerUtil;

/**
 * An abstract base class that can be used to conveniently extends to get common functionality (for example a logger).
 *
 * @author Scot P. Floess
 */
public abstract class AbstractBase {
    /**
     * Our logger.
     */
    private final Logger logger;

    /**
     * Default constructor.
     */
    protected AbstractBase() {
        this.logger = Logger.getLogger(getClass().getName());
    }

    /**
     * Return the logger.
     *
     * @return the logger.
     */
    protected Logger getLogger() {
        return logger;
    }

    /**
     * Performs a log using the format arguments <code>objs</code> as an array that can be presented to the logger.
     *
     * @param level     the level of the log.
     * @param throwable the throwable to log with <code>str</code>.
     * @param str       the format string.
     * @param objs      a format arguments that are converted to an object array for logging.
     */
    protected void log(final Level level, final Throwable throwable, final String str, final Object... objs) {
        LoggerUtil.log(getLogger(), level, throwable, str, objs);
    }

    /**
     * Performs a log using the format arguments <code>objs</code> as an array that can be presented to the logger.
     *
     * @param level     the level of the log.
     * @param throwable the throwable to log with <code>str</code>.
     * @param str       the format string.
     */
    protected void log(final Level level, final Throwable throwable, final String str) {
        LoggerUtil.log(getLogger(), level, throwable, str);
    }

    /**
     * Performs a log using the format arguments <code>objs</code> as an array that can be presented to the logger.
     *
     * @param level the level of the log.
     * @param str   the format string.
     * @param objs  a format arguments that are converted to an object array for logging.
     */
    protected void log(final Level level, final String str, final Object... objs) {
        LoggerUtil.log(getLogger(), level, str, objs);
    }

    /**
     * Log and return the value.
     *
     * @param <V>    the type of data to return.
     *
     * @param level  the level to log at.
     * @param str    the log string.
     * @param retVal the value to return.
     *
     * @return the object logged.
     */
    protected <V> V logAndReturn(final Level level, final String str, final V retVal) {
        return LoggerUtil.logAndReturn(getLogger(), level, str, retVal);
    }

    /**
     * Log and return the value that are found at <code>index</code> in the format arguments <code>objs</code>. The "i" in LogFi stands for
     * integer position. Without a unique name on this method, there is conflict in calling the logAndReturn() counterpart.
     *
     * @param <V>   the type to return.
     *
     * @param level the level of the log.
     * @param str   the format string.
     * @param index the index into <code>objs</code> that is the return value.
     * @param objs  a format arguments that are converted to an object array for logging.
     *
     * @return the value found at index <code>index</code> in the format arguments <code>objs</code>.
     */
    protected <V> V logAndReturnByIndex(final Level level, final String str, final int index, final Object... objs) {
        return LoggerUtil.logAndReturnByIndex(getLogger(), level, str, index, objs);
    }

    /**
     * Log and return the value at found as the 0th index in the format arguments <code>objs</code>.
     *
     * @param <V>   the type to return.
     *
     * @param level the level of the log.
     * @param str   the format string.
     * @param objs  a format arguments that are converted to an object array for logging.
     *
     * @return the value found at 0th index in the format arguments <code>objs</code>.
     */
    protected <V> V logAndReturn(final Level level, final String str, final Object... objs) {
        return LoggerUtil.logAndReturn(getLogger(), level, str, objs);
    }

    /**
     * Convenience method to log at INFO level.
     *
     * @param str the format string
     * @param objs a format arguments converted to an object array for logging
     */
    protected void logInfo(final String str, final Object... objs) {
        log(Level.INFO, str, objs);
    }

    /**
     * Convenience method to log at WARNING level.
     *
     * @param str the format string
     * @param objs a format arguments converted to an object array for logging
     */
    protected void logWarning(final String str, final Object... objs) {
        log(Level.WARNING, str, objs);
    }

    /**
     * Convenience method to log at WARNING level with a throwable.
     *
     * @param throwable the throwable to log
     * @param str the format string
     * @param objs a format arguments converted to an object array for logging
     */
    protected void logWarning(final Throwable throwable, final String str, final Object... objs) {
        log(Level.WARNING, throwable, str, objs);
    }

    /**
     * Convenience method to log at SEVERE level.
     *
     * @param str the format string
     * @param objs a format arguments converted to an object array for logging
     */
    protected void logError(final String str, final Object... objs) {
        log(Level.SEVERE, str, objs);
    }

    /**
     * Convenience method to log at SEVERE level with a throwable.
     *
     * @param throwable the throwable to log
     * @param str the format string
     * @param objs a format arguments converted to an object array for logging
     */
    protected void logError(final Throwable throwable, final String str, final Object... objs) {
        log(Level.SEVERE, throwable, str, objs);
    }
}
