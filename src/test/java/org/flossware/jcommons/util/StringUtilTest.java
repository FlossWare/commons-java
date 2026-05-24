package org.flossware.jcommons.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilTest {

    @Test
    void testRequireNonBlank_withValidString() {
        String result = StringUtil.requireNonBlank("valid", "error");
        assertEquals("valid", result);
    }

    @Test
    void testRequireNonBlank_withBlankString() {
        assertThrows(IllegalArgumentException.class, () ->
            StringUtil.requireNonBlank("", "String cannot be blank"));
    }

    @Test
    void testRequireNonBlank_withNullString() {
        assertThrows(IllegalArgumentException.class, () ->
            StringUtil.requireNonBlank(null, "String cannot be blank"));
    }

    @Test
    void testRequireNonBlank_withWhitespaceString() {
        assertThrows(IllegalArgumentException.class, () ->
            StringUtil.requireNonBlank("   ", "String cannot be blank"));
    }

    @Test
    void testRequireNonBlank_withDefaultMessage() {
        assertThrows(IllegalArgumentException.class, () ->
            StringUtil.requireNonBlank(""));
    }

    @Test
    void testAsUrlEncoded() {
        assertEquals("hello+world", StringUtil.asUrlEncoded("hello world"));
        assertEquals("test%3Dvalue%26other", StringUtil.asUrlEncoded("test=value&other"));
    }

    @Test
    void testEnsureString_deprecated() {
        assertEquals("test", StringUtil.ensureString("test"));
    }

    @Test
    void testEnsureString_throwsOnBlank() {
        assertThrows(IllegalArgumentException.class, () ->
            StringUtil.ensureString(""));
    }

    @Test
    void testConcat_simpleObjects() {
        String result = StringUtil.concat("Hello", " ", "World");
        assertEquals("Hello World", result);
    }

    @Test
    void testConcat_withNumbers() {
        String result = StringUtil.concat("Count: ", 42);
        assertEquals("Count: 42", result);
    }

    @Test
    void testConcatWithSeparator() {
        String result = StringUtil.concatWithSeparator(", ", "apple", "banana", "cherry");
        assertEquals("apple, banana, cherry", result);
    }

    @Test
    void testConcatWithSeparator_atEnd() {
        String result = StringUtil.concatWithSeparator(true, ", ", "apple", "banana");
        assertEquals("apple, banana, ", result);
    }

    @Test
    void testIsContained_true() {
        assertTrue(StringUtil.isContained("hello world", "world"));
    }

    @Test
    void testIsContained_false() {
        assertFalse(StringUtil.isContained("hello world", "foo"));
    }

    @Test
    void testIsContained_nullString() {
        assertFalse(StringUtil.isContained(null, "test"));
    }

    @Test
    void testGenerateUniqueString() {
        String result1 = StringUtil.generateUniqueString();
        String result2 = StringUtil.generateUniqueString();

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotEquals(result1, result2);
    }

    @Test
    void testGenerateUniqueString_withPrefix() {
        String result = StringUtil.generateUniqueString("test-");

        assertNotNull(result);
        assertTrue(result.startsWith("test-"));
    }

    @Test
    void testGenerateUniqueString_withPrefixAndSuffix() {
        String result = StringUtil.generateUniqueString("prefix-", "-suffix");

        assertNotNull(result);
        assertTrue(result.startsWith("prefix-"));
        assertTrue(result.endsWith("-suffix"));
    }

    @Test
    void testToStringAndFromString_serialization() {
        String testString = "test data";

        String serialized = StringUtil.toString(testString);
        assertNotNull(serialized);

        String deserialized = StringUtil.fromString(serialized);
        assertEquals(testString, deserialized);
    }

    @Test
    void testToString_throwsOnNull() {
        assertThrows(IllegalArgumentException.class, () ->
            StringUtil.toString(null));
    }

    @Test
    void testFromString_throwsOnNull() {
        assertThrows(IllegalArgumentException.class, () ->
            StringUtil.fromString(null));
    }

    @Test
    void testFromString_throwsOnEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
            StringUtil.fromString(""));
    }

    @Test
    void testToCompressedStringAndFromCompressed() {
        String testString = "test data that should be compressed";

        String compressed = StringUtil.toCompressedString(testString);
        assertNotNull(compressed);

        String decompressed = StringUtil.fromCompressedString(compressed);
        assertEquals(testString, decompressed);
    }

    @Test
    void testToCompressedString_throwsOnNull() {
        assertThrows(IllegalArgumentException.class, () ->
            StringUtil.toCompressedString(null));
    }

    @Test
    void testFromCompressedString_throwsOnNull() {
        assertThrows(IllegalArgumentException.class, () ->
            StringUtil.fromCompressedString((String) null));
    }

    @Test
    void testFromCompressedString_throwsOnEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
            StringUtil.fromCompressedString(""));
    }

    @Test
    void testIsContained_withNullString() {
        assertFalse(StringUtil.isContained(null, "test"));
    }

    @Test
    @SuppressWarnings("deprecation")
    void testToString_withNullSerializable() {
        assertThrows(IllegalArgumentException.class, () ->
            StringUtil.toString((java.io.Serializable) null));
    }

    @Test
    @SuppressWarnings("deprecation")
    void testToCompressedString_withNullSerializable() {
        assertThrows(IllegalArgumentException.class, () ->
            StringUtil.toCompressedString((java.io.Serializable) null));
    }

    @Test
    @SuppressWarnings("deprecation")
    void testFromString_withNullResult() {
        // Create an invalid base64 string that will decode but fail deserialization
        String invalidData = java.util.Base64.getEncoder().encodeToString(new byte[]{1, 2, 3});
        assertThrows(RuntimeException.class, () ->
            StringUtil.fromString(invalidData));
    }

    @Test
    @SuppressWarnings("deprecation")
    void testFromCompressedString_withNullResult() {
        // Create an invalid compressed base64 string
        String invalidData = java.util.Base64.getEncoder().encodeToString(new byte[]{1, 2, 3});
        assertThrows(RuntimeException.class, () ->
            StringUtil.fromCompressedString(invalidData));
    }

    @Test
    void testIsSeparatorAppendable_withNullObjs() throws Exception {
        // Test the private static method isSeparatorAppendable with null
        java.lang.reflect.Method method = StringUtil.class.getDeclaredMethod(
            "isSeparatorAppendable", String.class, int.class, Object[].class);
        method.setAccessible(true);
        Boolean result = (Boolean) method.invoke(null, "-", 0, (Object[]) null);
        assertFalse(result);
    }

    @Test
    void testToStream_withNullSerializable() throws Exception {
        // Test package-private toStream method with null
        java.lang.reflect.Method method = StringUtil.class.getDeclaredMethod(
            "toStream", java.io.OutputStream.class, java.io.Serializable.class);
        method.setAccessible(true);

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        method.invoke(null, baos, null);
        // Should log warning and return without writing
        assertEquals(0, baos.size());
    }

    @Test
    void testToCompressedStream_withNullSerializable() throws Exception {
        // Test package-private toCompressedStream method with null
        java.lang.reflect.Method method = StringUtil.class.getDeclaredMethod(
            "toCompressedStream", java.io.ByteArrayOutputStream.class, java.io.Serializable.class);
        method.setAccessible(true);

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        method.invoke(null, baos, null);
        // Should log warning and return without writing
        assertEquals(0, baos.size());
    }

    @Test
    void testFromStream_withNullInputStream() throws Exception {
        // Test package-private fromStream method with null
        java.lang.reflect.Method method = StringUtil.class.getDeclaredMethod(
            "fromStream", java.io.InputStream.class);
        method.setAccessible(true);

        Object result = method.invoke(null, (java.io.InputStream) null);
        assertNull(result);
    }

    @Test
    void testFromCompressedStream_withNullInputStream() throws Exception {
        // Test package-private fromCompressedStream method with null ByteArrayInputStream
        java.lang.reflect.Method method = StringUtil.class.getDeclaredMethod(
            "fromCompressedString", java.io.ByteArrayInputStream.class);
        method.setAccessible(true);

        Object result = method.invoke(null, (java.io.ByteArrayInputStream) null);
        assertNull(result);
    }

    @Test
    void testPrivateConstructor() throws Exception {
        Constructor<StringUtil> constructor = StringUtil.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
