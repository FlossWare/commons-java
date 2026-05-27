/*
 * Copyright (C) 2015 sfloess
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

import java.util.Objects;

/**
 * Utility class for class functionality.
 *
 * @author sfloess
 */
public final class ClassUtil {

    /**
     * Compute the package for klass.
     *
     * @param klass the class for whom we desire a package.
     *
     * @return the package name for klass, or empty string if the class is in the default package,
     *         is a primitive type, or is an array type with component type in the default package.
     */
    public static String getPackageName(final Class<?> klass) {
        return Objects.requireNonNull(klass, "Class must not be null").getPackageName();
    }

    /**
     * Return the class for object.
     *
     * @param <T>    the type of class.
     * @param object the object for whom we desire a class.
     *
     * @return the class for object.
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClass(final Object object) {
        // Unchecked cast is unavoidable: getClass() returns Class<?>, but we parameterize
        // the return type based on caller's context. Safe in practice as the cast matches
        // the actual runtime type, though type parameter T cannot be verified at compile time.
        return (Class<T>) Objects.requireNonNull(object, "Object must not be null").getClass();
    }

    /**
     * Default constructor not allowed.
     */
    private ClassUtil() {
        throw new AssertionError("Utility class - do not instantiate");
    }
}
