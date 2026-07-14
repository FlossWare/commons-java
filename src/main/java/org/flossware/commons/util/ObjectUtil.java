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

import java.util.Objects;
import java.util.logging.Logger;

/**
 *
 * Utility class in the spirit of java.util.Objects.
 *
 * @author Scot P. Floess
 * @since 1.0
 *
 */
public final class ObjectUtil {
    private static final Logger LOGGER = Logger.getLogger(ObjectUtil.class.getName());

    /**
     * Default error message when a parameter is bad.
     *
     * @since 1.0
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
     *
     * @since 1.0
     */
    public static String getPackage(final Object object) {
        return ClassUtil.getPackageName(Objects.requireNonNull(object, "Object must not be null").getClass());
    }
}
