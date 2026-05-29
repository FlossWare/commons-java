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

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClassUtilTest {

    @Test
    void testGetPackageName_withValidClass() {
        String packageName = ClassUtil.getPackageName(String.class);
        assertEquals("java.lang", packageName);
    }

    @Test
    void testGetPackageName_withNullClass() {
        assertThrows(NullPointerException.class, () ->
            ClassUtil.getPackageName(null));
    }

    @Test
    void testGetPackageName_withCustomClass() {
        String packageName = ClassUtil.getPackageName(ClassUtilTest.class);
        assertEquals("org.flossware.commons.util", packageName);
    }

    @Test
    void testGetClass_withValidObject() {
        String obj = "test";
        Class<String> clazz = ClassUtil.getClass(obj);
        assertEquals(String.class, clazz);
    }

    @Test
    void testGetClass_withNullObject() {
        assertThrows(NullPointerException.class, () ->
            ClassUtil.getClass(null));
    }

    @Test
    void testGetClass_withInteger() {
        Integer num = 42;
        Class<Integer> clazz = ClassUtil.getClass(num);
        assertEquals(Integer.class, clazz);
    }

    @Test
    void testPrivateConstructor() throws Exception {
        java.lang.reflect.Constructor<ClassUtil> constructor = ClassUtil.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);

        var exception = assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof AssertionError);
        assertEquals("Utility class - do not instantiate", exception.getCause().getMessage());
    }

}
