package org.flossware.jcommons.util;

import java.util.Objects;
import java.util.logging.Logger;

/**
 *
 * Utility class in the spirit of java.util.Objects.
 *
 * @author Scot P. Floess
 *
 */
public final class ObjectUtil {
    /**
     * Our logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ObjectUtil.class.getName());

    /**
     * Return the logger.
     */
    private static Logger getLogger() {
        return LOGGER;
    }

    /**
     * Default error message when a parameter is bad.
     */
    public static final String DEFAULT_ERROR_MSG = "Invalid value";


    /**
     * Default constructor not allowed.
     */
    private ObjectUtil() {
        throw new AssertionError("Utility class - do not instantiate");
    }

    /**
     * Compute the package for object.
     *
     * @param object the object for whom we desire a package.
     *
     * @return the package for object.
     */
    public static String getPackage(final Object object) {
        return ClassUtil.getPackageName(Objects.requireNonNull(object, "Object must not be null").getClass());
    }
}
