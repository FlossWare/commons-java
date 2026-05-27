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

package org.flossware.jcommons.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Utility class for URL parsing, creation, and manipulation.
 * Provides methods for extracting protocol and host information from URLs.
 *
 * @author Scot P. Floess
 */
public final class UrlUtil {

    /**
     * Protocol separator.
     */
    public static final String PROTOCOL_SEPARATOR = "://";

    /**
     * Private constructor to prevent instantiation.
     */
    private UrlUtil() {
        throw new AssertionError("Utility class - do not instantiate");
    }

    /**
     * Converts <code>rawURL</code> to a URL.
     *
     * @param rawUrl the complete raw URL (including any additional paths).
     *
     * @return a URL.
     *
     * @throws NullPointerException if rawUrl is null
     * @throws IllegalArgumentException if computing the URL fails.
     */
    public static URL createUrl(final String rawUrl) {
        Objects.requireNonNull(rawUrl, "URL must not be null");

        try {
            return new URL(rawUrl);
        } catch (final MalformedURLException exception) {
            throw new IllegalArgumentException("Trouble getting protocol and host!", exception);
        }
    }

    /**
     * Extract protocol and host from a URL.
     *
     * @param url the URL to extract from
     * @return string in format "protocol://host"
     * @throws NullPointerException if url is null
     */
    public static String asProtocolAndHost(final URL url) {
        Objects.requireNonNull(url, "URL must not be null");

        return StringUtil.concat(url.getProtocol(), PROTOCOL_SEPARATOR, url.getHost());
    }

    /**
     * Taking the <code>rawURL</code>, will return the URL for just the server.
     *
     * @param rawUrl The complete raw URL (including any additional paths).
     *
     * @return the server's URL including protocol.
     *
     * @throws NullPointerException if rawUrl is null
     * @throws IllegalArgumentException if computing the URL fails.
     */
    public static String computeHostUrlAsString(final String rawUrl) {
        Objects.requireNonNull(rawUrl, "URL must not be null");

        return asProtocolAndHost(createUrl(rawUrl));
    }

    /**
     * Using <code>rawUrl</code>, convert to protocol and host version.
     *
     * @param rawUrl the raw URL to convert.
     *
     * @return a URL representation of only protocol and host.
     * @throws NullPointerException if rawUrl is null
     * @throws UrlException if the URL protocol does not support standard host extraction (e.g., jar:, file:)
     */
    public static URL computeHostUrl(final String rawUrl) {
        Objects.requireNonNull(rawUrl, "URL must not be null");

        try {
            return new URL(computeHostUrlAsString(rawUrl));
        } catch (final MalformedURLException malformedUrlException) {
            // Some protocols like jar: have complex syntax that doesn't fit "protocol://host" format
            throw new UrlException("Unable to compute host URL for: " + rawUrl + ". Protocol may not support standard host extraction.", malformedUrlException);
        }
    }

}
