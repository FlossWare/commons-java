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
package org.flossware.commons;

/**
 *
 * API to convert an object to a StringBuilder and makes it pretty.
 *
 * @author Scot P. Floess
 *
 */
public interface Stringifiable {
    /**
     * System property name for line separator.
     */
    String LINE_SEPARATOR_PROPERTY = "line.separator";

    /**
     * The line separator string for this system.
     */
    String LINE_SEPARATOR_STRING = System.getProperty(LINE_SEPARATOR_PROPERTY);

    /**
     * A default prefix to use.
     */
    String DEFAULT_PREFIX = "    ";

    /**
     * Using <code>stringBuilder</code>, compute the string representation of self whose prefix for computation is
     * <code>prefix</code>.
     *
     * @param stringBuilder will have the string representation of self appended.
     * @param prefix        is the prefix to be first appended prior to self's string representation.
     *
     * @return a string builder that can be reused.
     */
    StringBuilder toStringBuilder(StringBuilder stringBuilder, String prefix);

    /**
     * Using <code>stringBuilder</code>, compute the string representation of self.
     *
     * @param stringBuilder will have the string representation of self appended.
     *
     * @return a string builder that can be reused.
     */
    StringBuilder toStringBuilder(StringBuilder stringBuilder);

    /**
     * Using <code>prefix</code> return the string representation of self.
     *
     * @param prefix is the prefix to emit when return the string representation of self.
     *
     * @return the string representation of self.
     */
    StringBuilder toStringBuilder(String prefix);
}
