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
        constructor.newInstance();
    }

    @Test
    void testPrivateGetLogger() throws Exception {
        java.lang.reflect.Method method = ObjectUtil.class.getDeclaredMethod("getLogger");
        method.setAccessible(true);
        Object logger = method.invoke(null);
        assertNotNull(logger);
    }
}
