package org.flossware.jcommons.util;

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
        assertEquals("org.flossware.jcommons.util", packageName);
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
