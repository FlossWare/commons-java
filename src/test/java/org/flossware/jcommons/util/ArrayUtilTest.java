package org.flossware.jcommons.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayUtilTest {

    @Test
    void testEnsureArray_withValidArray() {
        String[] array = {"one", "two", "three"};
        String[] result = ArrayUtil.ensureArray(array);
        assertSame(array, result);
    }

    @Test
    void testEnsureArray_withNullArray() {
        assertThrows(NullPointerException.class, () ->
            ArrayUtil.ensureArray(null));
    }

    @Test
    void testEnsureArray_withEmptyArray() {
        String[] array = {};
        assertThrows(IllegalArgumentException.class, () ->
            ArrayUtil.ensureArray(array));
    }

    @Test
    void testEnsureArray_withNullElement() {
        String[] array = {"one", null, "three"};
        assertThrows(NullPointerException.class, () ->
            ArrayUtil.ensureArray(array));
    }

    @Test
    void testEnsureArray_withMinLength() {
        String[] array = {"one", "two", "three"};
        String[] result = ArrayUtil.ensureArray(array, 3);
        assertSame(array, result);
    }

    @Test
    void testEnsureArray_withMinLength_tooShort() {
        String[] array = {"one", "two"};
        assertThrows(IllegalArgumentException.class, () ->
            ArrayUtil.ensureArray(array, 3));
    }

    @Test
    void testEnsureArray_withCustomMessage() {
        String[] array = {};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            ArrayUtil.ensureArray(array, "Custom error message"));
        assertEquals("Custom error message", exception.getMessage());
    }

    @Test
    void testEnsureArray_withMinLengthAndMessage() {
        String[] array = {"one"};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            ArrayUtil.ensureArray(array, 2, "Need at least 2 elements"));
        assertEquals("Need at least 2 elements", exception.getMessage());
    }

    @Test
    void testEnsureArray_singleElement() {
        String[] array = {"only"};
        String[] result = ArrayUtil.ensureArray(array);
        assertSame(array, result);
    }

    @Test
    void testDefaultMinArrayLength() {
        assertEquals(1, ArrayUtil.DEFAULT_MIN_ARRAY_LENGTH);
    }

    @Test
    void testPrivateConstructor() throws Exception {
        Constructor<ArrayUtil> constructor = ArrayUtil.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);

        var exception = assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof AssertionError);
        assertEquals("Utility class - do not instantiate", exception.getCause().getMessage());
    }

}
