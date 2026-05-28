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

import org.flossware.commons.util.StringUtil;

/**
 *
 * Default abstract base class of stringifiables.
 *
 * @author Scot P. Floess
 *
 */
public abstract class AbstractStringifiable extends AbstractBase implements Stringifiable {
    /**
     * Default constructor.
     */
    protected AbstractStringifiable() {
    }

    /**
     * Appends a line to the StringBuilder by concatenating all line parts and adding a line separator.
     *
     * @param stringBuilder the StringBuilder to append to
     * @param line the line parts to concatenate and append
     * @return the StringBuilder with the appended line
     */
    protected StringBuilder appendLine(final StringBuilder stringBuilder, final String... line) {
        return StringUtil.concat(stringBuilder, (Object[]) line).append(LINE_SEPARATOR_STRING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringBuilder toStringBuilder(final StringBuilder stringBuilder) {
        return toStringBuilder(stringBuilder, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringBuilder toStringBuilder(final String prefix) {
        return toStringBuilder(new StringBuilder(), prefix);
    }

    /**
     * {@inheritDoc}
     *
     * @return the string representation of self.
     */
    @Override
    public String toString() {
        return toStringBuilder("").toString();
    }
}
