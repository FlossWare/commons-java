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

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObjectUtilTest {

    @Test
    void testGetPackage_withValidObject() {
        String obj = "test";
        String packageName = ObjectUtil.getPackage(obj);
        assertEquals("java.lang", packageName);
    }

    @Test
    void testGetPackage_withNullObject() {
        assertThrows(NullPointerException.class, () ->
            ObjectUtil.getPackage(null));
    }

    @Test
    void testGetPackage_withCustomClass() {
        ObjectUtilTest obj = new ObjectUtilTest();
        String packageName = ObjectUtil.getPackage(obj);
        assertEquals("org.flossware.jcommons.util", packageName);
    }

    @Test
    void testDefaultErrorMessage() {
        assertEquals("Invalid value", ObjectUtil.DEFAULT_ERROR_MSG);
    }

    @Test
    void testPrivateConstructor() throws Exception {
        Constructor<ObjectUtil> constructor = ObjectUtil.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);

        var exception = assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof AssertionError);
        assertEquals("Utility class - do not instantiate", exception.getCause().getMessage());
    }

}
