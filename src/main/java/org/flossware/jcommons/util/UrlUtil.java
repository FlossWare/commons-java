package org.flossware.jcommons.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Utility class for URL parsing, creation, and manipulation.
 * Provides methods for extracting protocol and host information from URLs.
 *
 * @author sfloess
 */
public class UrlUtil {

    /**
     * Protocol separator.
     */
    public static final String PROTOCOL_SEPARATOR = "://";

    /**
     * Converts <code>rawURL</code> to a URL.
     *
     * @param rawUrl the complete raw URL (including any additional paths).
     *
     * @return a URL.
     *
     * @throws IllegalArgumentException if computing the URL fails.
     */
    public static URL createUrl(final String rawUrl) {
        try {
            return new URL(rawUrl);
        } catch (final MalformedURLException exception) {
//            getLogger().log(Level.SEVERE, "Trouble getting protocol and host [{0}]", exception.getMessage());

            throw new IllegalArgumentException("Trouble getting protocol and host!", exception);
        }
    }

    public static String asProtocolAndHost(final URL url) {
        return StringUtil.concat(url.getProtocol(), PROTOCOL_SEPARATOR, url.getHost());
    }

    /**
     * Taking the <code>rawURL</code>, will return the URL for just the server.
     *
     * @param rawUrl The complete raw URL (including any additional paths).
     *
     * @return the server's URL including protocol.
     *
     * @throws IllegalArgumentException if computing the URL fails.
     */
    public static String computeHostUrlAsString(final String rawUrl) {
        return asProtocolAndHost (createUrl(rawUrl));
    }

    /**
     * Using <code>rawUrl</code>, convert to protocol and host version.
     *
     * @param rawUrl the raw URL to convert.
     *
     * @return a URL representation of only protocol and host.
     */
    public static URL computeHostUrl(final String rawUrl) {
        try {
            return new URL(computeHostUrlAsString(rawUrl));
        } catch (final MalformedURLException malformedUrlException) {
            throw new UrlException(malformedUrlException);
        }
    }

}
