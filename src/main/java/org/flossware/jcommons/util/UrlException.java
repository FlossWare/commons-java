
package org.flossware.jcommons.util;

/**
 * Exception thrown when URL operations fail.
 * Used for URL parsing, creation, and manipulation errors.
 *
 * @author Scot P. Floess
 */
public class UrlException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public UrlException() {
    }

    /**
     * Sets the message.
     *
     * @param message detail message.
     */
    public UrlException(final String message) {
        super(message);
    }

    /**
     * Sets the cause of why self is being raised.
     *
     * @param cause the cause of why self is being raised.
     */
    public UrlException(final Throwable cause) {
        super(cause);
    }

    /**
     * Sets the cause of why self is raised and a message about it.
     *
     * @param message detail message.
     * @param cause   the cause of why self is being raised.
     */
    public UrlException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
