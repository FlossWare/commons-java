package org.flossware.jcommons.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.lang3.StringUtils;

/**
 * Same vein as <code>java.util.Objects</code>
 *
 * @author sfloess
 */
public final class StringUtil {
    /**
     * Our logger.
     */
    private static final Logger LOGGER = Logger.getLogger(StringUtil.class.getName());

    /**
     * Return the logger.
     */
    private static Logger getLogger() {
        return LOGGER;
    }

    /**
     * Default error message for blank string validation.
     */
    public static final String STRING_CANNOT_BE_BLANK = "String cannot be blank!";

    /**
     * Default separator.
     */
    public static final String DEFAULT_SEPARATOR = "";

    /**
     * Validates that a string is not null, empty, or whitespace-only.
     *
     * @param string the string to validate
     * @param message the error message to use if validation fails
     * @return the validated string
     * @throws IllegalArgumentException if the string is blank
     */
    public static String requireNonBlank(final String string, final String message) {
        if (StringUtils.isBlank(string)) {
            LoggerUtil.log(getLogger(), Level.SEVERE, "String is empty [", string, "]");

            throw new IllegalArgumentException(message);
        }

        return string;
    }

    /**
     * Validates that a string is not null, empty, or whitespace-only using default error message.
     *
     * @param string the string to validate
     * @return the validated string
     * @throws IllegalArgumentException if the string is blank
     */
    public static String requireNonBlank(final String string) {
        return requireNonBlank(string, STRING_CANNOT_BE_BLANK);
    }


    /**
     * URL encodes a string using UTF-8 encoding.
     *
     * @param str the string to URL encode
     * @return the URL-encoded string
     */
    public static String asUrlEncoded(final String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }


    /**
     * Checks <code>str</code> to ensure it is not null nor empty.
     * Delegates to requireNonBlank for validation.
     *
     * @param str The string to inspect to ensure its not null nor empty.
     * @param errorMsg The error message within the raised exception if
     * <code>str</code> is null or empty.
     *
     * @return str if it is not null or empty.
     *
     * @throws IllegalArgumentException if <code>str</code> is blank.
     * @deprecated Use {@link #requireNonBlank(String, String)} instead
     */
    @Deprecated
    public static String ensureString(final String str, final String errorMsg) throws IllegalArgumentException {
        return requireNonBlank(str, errorMsg);
    }

    /**
     * Checks <code>str</code> to ensure it is not null nor empty.
     * Delegates to requireNonBlank for validation.
     *
     * @param str The string to inspect to ensure its not null nor empty.
     *
     * @return str if it is not null or empty.
     *
     * @throws IllegalArgumentException if <code>str</code> is blank.
     * @deprecated Use {@link #requireNonBlank(String)} instead
     */
    @Deprecated
    public static String ensureString(final String str) throws IllegalArgumentException {
        return requireNonBlank(str);
    }

    /**
     * Return true if a separator can be appended or false if not. To append,
     * the index must be less than or equal to the array's length - 2.
     *
     * @param separator the separator string to check against
     * @param index the place within the array we are processing
     * @param objs the array of objects being processed
     *
     * @return true if we can append a separator or false if not
     */
    private static boolean isSeparatorAppendable(final String separator, final int index, final Object... objs) {
        if (objs == null || index < 0 || index >= objs.length) {
            return false;
        }

        if (objs[index] == null) {
            return false;
        }

        boolean isNotLastElement = index <= (objs.length - 2);
        boolean doesNotEndWithSeparator = !objs[index].toString().endsWith(separator);
        boolean canAppend = isNotLastElement && doesNotEndWithSeparator;

        LoggerUtil.log(getLogger(), Level.FINEST, "Is the separator appendable [{0}] for index [{1}]", canAppend, index);

        return canAppend;
    }

    /**
     * Concat objects together in <code>stringBuilder</code> using the toString
     * of the <code>objs</code>.
     *
     * @param stringBuilder the string builder form whom we will concatenate.
     * @param isSeparatorAtEnd if true denotes we will always have the separator
     * appended.
     * @param separator the separator to use between concatenation.
     * @param objs the objects to concatenate.
     *
     * @return the string builder with concatendated data.
     * @throws NullPointerException if separator is null
     */
    public static StringBuilder concatWithSeparator(final StringBuilder stringBuilder, final boolean isSeparatorAtEnd, final String separator, Object... objs) {
        Objects.requireNonNull(separator, "Separator must not be null");
        ArrayUtil.ensureArray(objs, "Objects must not be null");

        for (int index = 0; index < objs.length; index++) {
            stringBuilder.append(objs[index]);

            if (isSeparatorAppendable(separator, index, objs)) {
                stringBuilder.append(separator);
            }
        }

        if (isSeparatorAtEnd) {
            stringBuilder.append(separator);
        }

        LoggerUtil.log(getLogger(), Level.FINEST, "Returning [{0}]", stringBuilder);

        return stringBuilder;
    }

    /**
     * Concat objects together and return the toString of the concatenation.
     *
     * @param isSeparatorAtEnd if true denotes we will always have the separator
     * appended.
     * @param separator the separator to use between concatenation.
     * @param objs the objects to concatenate.
     *
     * @return the string representation of the concatenation.
     */
    public static String concatWithSeparator(final boolean isSeparatorAtEnd, final String separator, Object... objs) {
        return concatWithSeparator(new StringBuilder(), isSeparatorAtEnd, separator, objs).toString();
    }

    /**
     * Concat objects together in <code>stringBuilder</code> using the toString
     * of the <code>objs</code>.
     *
     * @param stringBuilder the string builder form whom we will concatenate.
     * @param separator the separator to use between concatenation.
     * @param objs the objects to concatenate.
     *
     * @return the string representation of the concatenation.
     */
    public static StringBuilder concatWithSeparator(final StringBuilder stringBuilder, final String separator, Object... objs) {
        return concatWithSeparator(stringBuilder, false, separator, objs);
    }

    /**
     * Concat objects together and return the toString of the concatenation.
     *
     * @param separator the separator to use between concatenation.
     * @param objs the objects to concatenate.
     *
     * @return the string representation of the concatenation.
     */
    public static String concatWithSeparator(final String separator, Object... objs) {
        return concatWithSeparator(false, separator, objs);
    }

    /**
     * Concat objects together in <code>stringBuilder</code> using the toString
     * of the <code>objs</code>.
     *
     * @param stringBuilder the string builder form whom we will concatenate.
     * @param objs the objects to concatenate.
     *
     * @return the string representation of the concatenation.
     */
    public static StringBuilder concat(final StringBuilder stringBuilder, Object... objs) {
        return concatWithSeparator(stringBuilder, DEFAULT_SEPARATOR, objs);
    }

    /**
     * Concat objects together and return the toString of the concatenation.
     *
     * @param objs the objects to concatenate.
     *
     * @return the string representation of the concatenation.
     */
    public static String concat(Object... objs) {
        return concatWithSeparator(DEFAULT_SEPARATOR, objs);
    }

    /**
     * Return true if <code>str</code> contains <code>contains</code>.
     *
     * @param str is the string to examine for containing <code>contains</code>.
     * @param contains is the string to see if <code>str</code> is contained.
     *
     * @return true if <code>str</code> contains <code>contains</code>.
     */
    public static boolean isContained(final String str, final String contains) {
        final boolean retVal = (null != str) && str.contains(contains);

        LoggerUtil.log(getLogger(), Level.FINEST, "Exception message contained result [{0}] for [{1}] message [{2}]", retVal, contains, str);

        return retVal;
    }

    static void toStream(final OutputStream os, final Serializable serializable) {
        if (null == serializable) {
            LoggerUtil.log(getLogger(), Level.WARNING, "Cannot serialize a null object!");

            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(serializable);
        } catch (final IOException ioException) {
            LoggerUtil.log(getLogger(), Level.SEVERE, ioException, "Trouble serializing object as a string!");
        }
    }

    static void toCompressedStream(final ByteArrayOutputStream baos, final Serializable serializable) {
        if (null == serializable) {
            LoggerUtil.log(getLogger(), Level.WARNING, "Cannot serialize a null object!");

            return;
        }

        try {
            try (GZIPOutputStream gos = new GZIPOutputStream(baos)) {
                toStream(gos, serializable);
            }
        } catch (final IOException ioException) {
            // This should never occur with ByteArrayOutputStream, but if it does
            // (e.g., due to GZIP stream errors), it represents a serious problem
            LoggerUtil.log(getLogger(), Level.SEVERE, ioException, "Unexpected IOException during compression!");
            throw new RuntimeException("Failed to compress serialized object", ioException);
        }
    }

    /**
     * Serializes and compresses a Serializable object to a Base64-encoded string.
     *
     * <p><strong>SECURITY WARNING:</strong> Java serialization is inherently insecure when
     * deserializing untrusted data. This method should ONLY be used for trusted, internal data.
     * For external data or cross-system communication, use JSON (Jackson, Gson) or Protocol Buffers instead.</p>
     *
     * <p><strong>DEPRECATED:</strong> This method will be removed in a future version.
     * Migrate to JSON-based serialization for better security and interoperability.</p>
     *
     * @param serializable the object to serialize and compress
     * @return the compressed, Base64-encoded string representation
     *
     * @throws IllegalArgumentException if serializable is null
     * @throws RuntimeException if serialization fails
     *
     * @deprecated Use JSON libraries (Jackson, Gson) for safer serialization. This method
     *             relies on Java native serialization which has known security vulnerabilities.
     *             Scheduled for removal in version 2.0.
     */
    @Deprecated(since = "1.22", forRemoval = true)
    public static String toCompressedString(final Serializable serializable) {
        if (null == serializable) {
            LoggerUtil.log(getLogger(), Level.SEVERE, "Cannot serialize a null object!");
            throw new IllegalArgumentException("Cannot serialize a null object!");
        }

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        toCompressedStream(baos, serializable);

        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    /**
     * Serializes a Serializable object to a Base64-encoded string.
     *
     * <p><strong>SECURITY WARNING:</strong> Java serialization is inherently insecure when
     * deserializing untrusted data. This method should ONLY be used for trusted, internal data.
     * For external data or cross-system communication, use JSON (Jackson, Gson) or Protocol Buffers instead.</p>
     *
     * <p><strong>DEPRECATED:</strong> This method will be removed in a future version.
     * Migrate to JSON-based serialization for better security and interoperability.</p>
     *
     * @param serializable the object to serialize
     * @return the Base64-encoded string representation
     *
     * @throws IllegalArgumentException if serializable is null
     * @throws RuntimeException if serialization fails
     *
     * @deprecated Use JSON libraries (Jackson, Gson) for safer serialization. This method
     *             relies on Java native serialization which has known security vulnerabilities.
     *             Scheduled for removal in version 2.0.
     */
    @Deprecated(since = "1.22", forRemoval = true)
    public static String toString(final Serializable serializable) {
        if (null == serializable) {
            LoggerUtil.log(getLogger(), Level.SEVERE, "Cannot serialize a null object!");
            throw new IllegalArgumentException("Cannot serialize a null object!");
        }

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        toStream(baos, serializable);

        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    static <T extends Serializable> T fromStream(final InputStream is) {
        if (null == is) {
            LoggerUtil.log(getLogger(), Level.WARNING, "Cannot deserialize from a null input stream!");

            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            // Add security filter to restrict deserialization to trusted packages only
            ois.setObjectInputFilter(filterInfo -> {
                if (filterInfo.serialClass() != null) {
                    String className = filterInfo.serialClass().getName();
                    // Allow only trusted packages
                    if (className.startsWith("org.flossware.") ||
                        className.startsWith("java.lang.") ||
                        className.startsWith("java.util.") ||
                        className.startsWith("[L")) {  // Array types
                        return ObjectInputFilter.Status.ALLOWED;
                    }
                    LoggerUtil.log(getLogger(), Level.WARNING,
                        "Blocked deserialization of untrusted class: " + className);
                    return ObjectInputFilter.Status.REJECTED;
                }
                return ObjectInputFilter.Status.UNDECIDED;
            });

            return (T) ois.readObject();
        } catch (final IOException | ClassNotFoundException exception) {
            LoggerUtil.log(getLogger(), Level.SEVERE, exception, "Trouble deserializing object from stream!");
        }

        return null;
    }

    static <T extends Serializable> T fromCompressedString(final ByteArrayInputStream bais) {
        if (null == bais) {
            LoggerUtil.log(getLogger(), Level.WARNING, "Cannot deserialize from a null input stream!");

            return null;
        }

        try (GZIPInputStream gis = new GZIPInputStream(bais)) {
            return fromStream(gis);
        } catch (final IOException ioException) {
            LoggerUtil.log(getLogger(), Level.SEVERE, ioException, "Trouble decompressing and deserializing object from string!");
        }

        return null;
    }

    /**
     * Deserializes and decompresses an object from a Base64-encoded compressed string.
     *
     * <p><strong>CRITICAL SECURITY WARNING:</strong> Java deserialization of untrusted data
     * is a critical security vulnerability that can lead to Remote Code Execution (RCE).
     * NEVER deserialize data from untrusted sources, user input, or external systems.</p>
     *
     * <p><strong>DEPRECATED:</strong> This method will be removed in a future version.
     * Use JSON libraries (Jackson, Gson) for safer deserialization with proper validation.</p>
     *
     * <p>If you must use this method:</p>
     * <ul>
     *   <li>Only deserialize data you serialized yourself</li>
     *   <li>Only from trusted, authenticated sources</li>
     *   <li>Never from user input, network requests, or external files</li>
     *   <li>Consider implementing ObjectInputFilter for additional protection</li>
     * </ul>
     *
     * @param <T> the type of the object to deserialize
     * @param str the Base64-encoded compressed string to deserialize
     * @return the deserialized object
     *
     * @throws IllegalArgumentException if str is blank
     * @throws RuntimeException if deserialization fails
     *
     * @deprecated Use JSON libraries (Jackson, Gson) for safer deserialization. This method
     *             has critical security vulnerabilities. Scheduled for removal in version 2.0.
     */
    @Deprecated(since = "1.22", forRemoval = true)
    public static <T extends Serializable> T fromCompressedString(final String str) {
        if (StringUtils.isBlank(str)) {
            LoggerUtil.log(getLogger(), Level.SEVERE, "Cannot deserialize from an empty string!");
            throw new IllegalArgumentException("Cannot deserialize from an empty string!");
        }

        byte[] decodedBytes = Base64.getDecoder().decode(str);
        final ByteArrayInputStream bais = new ByteArrayInputStream(decodedBytes);
        T result = fromCompressedString(bais);
        if (result == null) {
            throw new RuntimeException("Failed to deserialize compressed string");
        }
        return result;
    }

    /**
     * Deserializes an object from a Base64-encoded string.
     *
     * <p><strong>CRITICAL SECURITY WARNING:</strong> Java deserialization of untrusted data
     * is a critical security vulnerability that can lead to Remote Code Execution (RCE).
     * NEVER deserialize data from untrusted sources, user input, or external systems.</p>
     *
     * <p><strong>DEPRECATED:</strong> This method will be removed in a future version.
     * Use JSON libraries (Jackson, Gson) for safer deserialization with proper validation.</p>
     *
     * <p>If you must use this method:</p>
     * <ul>
     *   <li>Only deserialize data you serialized yourself</li>
     *   <li>Only from trusted, authenticated sources</li>
     *   <li>Never from user input, network requests, or external files</li>
     *   <li>Consider implementing ObjectInputFilter for additional protection</li>
     * </ul>
     *
     * @param <T> the type of the object to deserialize
     * @param str the Base64-encoded string to deserialize
     * @return the deserialized object
     *
     * @throws IllegalArgumentException if str is blank
     * @throws RuntimeException if deserialization fails
     *
     * @deprecated Use JSON libraries (Jackson, Gson) for safer deserialization. This method
     *             has critical security vulnerabilities. Scheduled for removal in version 2.0.
     */
    @Deprecated(since = "1.22", forRemoval = true)
    public static <T extends Serializable> T fromString(final String str) {
        if (StringUtils.isBlank(str)) {
            LoggerUtil.log(getLogger(), Level.SEVERE, "Cannot deserialize from an empty string!");
            throw new IllegalArgumentException("Cannot deserialize from an empty string!");
        }

        byte[] decodedBytes = Base64.getDecoder().decode(str);
        final ByteArrayInputStream bais = new ByteArrayInputStream(decodedBytes);
        T result = fromStream(bais);
        if (result == null) {
            throw new RuntimeException("Failed to deserialize string");
        }
        return result;
    }

    /**
     * Generates a unique string with the string's prefix being prefix and suffix being suffix.
     * Uses UUID to ensure uniqueness even under high-concurrency scenarios.
     *
     * @param prefix the prefix.
     * @param suffix the suffix.
     *
     * @return a unique string containing prefix as prefix and suffix as suffix.
     */
    public static String generateUniqueString(final String prefix, final String suffix) {
        return prefix + UUID.randomUUID().toString() + suffix;
    }

    /**
     * Generate a unique string with prefix as the prefix.
     *
     * @param prefix the prefix.
     *
     * @return a unique string containing prefix as prefix.
     */
    public static String generateUniqueString(final String prefix) {
        return generateUniqueString(prefix, "");
    }

    /**
     * Generate a unique string.
     *
     * @return a unique string.
     */
    public static String generateUniqueString() {
        return generateUniqueString("");
    }

    /**
     * Default constructor not allowed
     */
    private StringUtil() {
        throw new AssertionError("Utility class - do not instantiate");
    }
}
