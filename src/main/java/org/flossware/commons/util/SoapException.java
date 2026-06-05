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

/**
 * Exception thrown when SOAP operations fail.
 * Used for SOAP service configuration, header manipulation, and endpoint errors.
 *
 * @author Scot P. Floess
 */
public class SoapException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     *
     * @deprecated since 1.32 - no production usage, provide message or cause instead
     */
    @Deprecated(since = "1.32", forRemoval = true)
    public SoapException() {
    }

    /**
     * Sets the message.
     *
     * @param message detail message.
     */
    public SoapException(final String message) {
        super(message);
    }

    /**
     * Sets the cause of why self is being raised.
     *
     * @param cause the cause of why self is being raised.
     */
    public SoapException(final Throwable cause) {
        super(cause);
    }

    /**
     * Sets the cause of why self is raised and a message about it.
     *
     * @param message detail message.
     * @param cause   the cause of why self is being raised.
     */
    public SoapException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
