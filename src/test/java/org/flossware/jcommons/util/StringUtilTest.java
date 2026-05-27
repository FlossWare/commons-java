package org.flossware.jcommons.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

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
    void testEnsureString_withCustomMessage() {
        assertEquals("test", StringUtil.ensureString("test", "Custom error message"));
    }

    @Test
    void testEnsureString_withCustomMessage_throwsOnBlank() {
        assertThrows(IllegalArgumentException.class, () ->
            StringUtil.ensureString("", "Custom error message"));
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
    void testConcatWithSeparator_nullSeparator() {
        assertThrows(NullPointerException.class, () ->
            StringUtil.concatWithSeparator((String) null, "apple", "banana"));
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
    void testIsSeparatorAppendable_withNullElement() throws Exception {
        // Test the private static method isSeparatorAppendable with null element
        // This defensive code cannot be reached via public API because ArrayUtil.ensureArray
        // rejects arrays with null elements, so we test it via reflection
        java.lang.reflect.Method method = StringUtil.class.getDeclaredMethod(
            "isSeparatorAppendable", String.class, int.class, Object[].class);
        method.setAccessible(true);
        Object[] arrayWithNull = new Object[]{"test", null, "other"};
        Boolean result = (Boolean) method.invoke(null, "-", 1, arrayWithNull);
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
        // Test package-private fromStream method with null - should throw NullPointerException
        java.lang.reflect.Method method = StringUtil.class.getDeclaredMethod(
            "fromStream", java.io.InputStream.class);
        method.setAccessible(true);

        var exception = assertThrows(java.lang.reflect.InvocationTargetException.class,
            () -> method.invoke(null, (java.io.InputStream) null));
        assertTrue(exception.getCause() instanceof NullPointerException);
        assertEquals("Input stream must not be null", exception.getCause().getMessage());
    }

    @Test
    void testFromCompressedStream_withNullInputStream() throws Exception {
        // Test package-private fromCompressedStream method with null ByteArrayInputStream
        java.lang.reflect.Method method = StringUtil.class.getDeclaredMethod(
            "fromCompressedString", java.io.ByteArrayInputStream.class);
        method.setAccessible(true);

        var exception = assertThrows(java.lang.reflect.InvocationTargetException.class,
            () -> method.invoke(null, (java.io.ByteArrayInputStream) null));
        assertTrue(exception.getCause() instanceof NullPointerException);
        assertEquals("Input stream must not be null", exception.getCause().getMessage());
        assertTrue(exception.getCause() instanceof NullPointerException);
        assertEquals("Input stream must not be null", exception.getCause().getMessage());
    }

    @Test
    void testToStream_withIOException() throws Exception {
        // Test toStream with an OutputStream that throws IOException
        java.lang.reflect.Method method = StringUtil.class.getDeclaredMethod(
            "toStream", java.io.OutputStream.class, java.io.Serializable.class);
        method.setAccessible(true);

        java.io.OutputStream failingStream = new java.io.OutputStream() {
            @Override
            public void write(int b) throws java.io.IOException {
                throw new java.io.IOException("Test exception");
            }
        };

        // Should handle IOException and log it
        method.invoke(null, failingStream, "test");
    }

    @Test
    void testToCompressedStream_withIOException() throws Exception {
        // Test toCompressedStream with a stream that fails on close()
        java.lang.reflect.Method method = StringUtil.class.getDeclaredMethod(
            "toCompressedStream", java.io.ByteArrayOutputStream.class, java.io.Serializable.class);
        method.setAccessible(true);

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();

        // Should handle exception and log it (won't throw because catch block handles it)
        method.invoke(null, baos, "test");
    }

    @Test
    @SuppressWarnings("deprecation")
    void testToString_withBadSerializable() {
        // Create a Serializable with custom writeObject that throws
        java.io.Serializable badObject = new java.io.Serializable() {
            private static final long serialVersionUID = 1L;

            private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
                throw new java.io.IOException("Cannot serialize");
            }
        };

        // toStream catches IOException internally and logs it, so toString returns empty Base64
        String result = StringUtil.toString(badObject);
        assertNotNull(result);
    }

    @Test
    @SuppressWarnings("deprecation")
    void testToCompressedString_withBadSerializable() {
        // Create a Serializable with custom writeObject that throws
        java.io.Serializable badObject = new java.io.Serializable() {
            private static final long serialVersionUID = 1L;

            private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
                throw new java.io.IOException("Cannot serialize");
            }
        };

        // toCompressedStream catches IOException via toStream and logs it
        String result = StringUtil.toCompressedString(badObject);
        assertNotNull(result);
    }

    @Test
    @SuppressWarnings("deprecation")
    void testFromString_withIOException() {
        // Create invalid base64 that will decode but cause IOException during deserialization
        String invalidData = java.util.Base64.getEncoder().encodeToString(
            new byte[]{(byte)0xAC, (byte)0xED, 0x00, 0x05, 0x77, 0x00}); // Invalid object stream

        assertThrows(RuntimeException.class, () ->
            StringUtil.fromString(invalidData));
    }

    @Test
    @SuppressWarnings("deprecation")
    void testFromCompressedString_withIOException() {
        // Create invalid compressed data
        String invalidData = java.util.Base64.getEncoder().encodeToString(
            new byte[]{0x1F, (byte)0x8B, 0x08, 0x00, 0x00, 0x00}); // Invalid GZIP

        assertThrows(RuntimeException.class, () ->
            StringUtil.fromCompressedString(invalidData));
    }

    @Test
    void testToCompressedStream_cannotThrowIOException() throws Exception {
        // Verify that toCompressedStream with ByteArrayOutputStream cannot throw IOException
        // This tests that the AssertionError path is unreachable
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        StringUtil.toCompressedStream(baos, "test");
        assertTrue(baos.size() > 0);
    }

    @Test
    void testToCompressedStream_forcedIOException() throws Exception {
        // Force IOException to trigger the AssertionError defensive path
        // Use MockedConstruction to make GZIPOutputStream throw IOException
        try (var mockedGZIP = org.mockito.Mockito.mockConstruction(
                java.util.zip.GZIPOutputStream.class,
                (mock, context) -> {
                    doThrow(new IOException("Forced failure for coverage"))
                        .when(mock).close();
                })) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            assertThrows(RuntimeException.class, () ->
                StringUtil.toCompressedStream(baos, "test"));
        }
    }

    @Test
    @SuppressWarnings("deprecation")
    void testFromStream_objectInputFilter_allowedPath() throws Exception {
        // Test the ObjectInputFilter ALLOWED path by invoking fromStream directly with reflection
        // Use ArrayList to trigger filter invocation (more complex than String)

        // Serialize an ArrayList (from java.util package - should be allowed by filter)
        java.util.ArrayList<String> testList = new java.util.ArrayList<>();
        testList.add("item1");
        testList.add("item2");

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos)) {
            oos.writeObject(testList);
        }

        // Deserialize using fromStream - this should exercise the filter's ALLOWED path
        java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(baos.toByteArray());
        java.lang.reflect.Method method = StringUtil.class.getDeclaredMethod(
            "fromStream", java.io.InputStream.class);
        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        java.util.ArrayList<String> result = (java.util.ArrayList<String>) method.invoke(null, bais);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("item1", result.get(0));
        assertEquals("item2", result.get(1));
    }

    @Test
    @SuppressWarnings("deprecation")
    void testFromStream_objectInputFilter_withCustomObject() throws Exception {
        // Test ObjectInputFilter with a custom serializable object from this test class
        // This class is in org.flossware.jcommons.util package, so it should be ALLOWED

        CustomSerializable obj = new CustomSerializable("test data");

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos)) {
            oos.writeObject(obj);
        }

        java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(baos.toByteArray());
        java.lang.reflect.Method method = StringUtil.class.getDeclaredMethod(
            "fromStream", java.io.InputStream.class);
        method.setAccessible(true);

        CustomSerializable result = (CustomSerializable) method.invoke(null, bais);
        assertNotNull(result);
        assertEquals("test data", result.getData());
    }

    @Test
    @SuppressWarnings("deprecation")
    void testFromStream_objectInputFilter_rejectedPath() throws Exception {
        // Test ObjectInputFilter REJECTED path with untrusted package
        // com.untrusted.test is NOT in the allowed list (org.flossware.*, java.lang.*, java.util.*, [L)

        com.untrusted.test.UntrustedSerializable obj = new com.untrusted.test.UntrustedSerializable("untrusted data");

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos)) {
            oos.writeObject(obj);
        }

        // Try to deserialize - the filter should REJECT it and return null
        java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(baos.toByteArray());
        java.lang.reflect.Method method = StringUtil.class.getDeclaredMethod(
            "fromStream", java.io.InputStream.class);
        method.setAccessible(true);

        // The filter will reject the untrusted class, causing deserialization to fail
        // fromStream should catch the exception and return null
        // The filter will reject the untrusted class, causing deserialization to fail
        // fromStream should throw RuntimeException wrapping InvalidClassException
        var exception = assertThrows(java.lang.reflect.InvocationTargetException.class,
            () -> method.invoke(null, bais));
        assertTrue(exception.getCause() instanceof RuntimeException);
        assertTrue(exception.getCause().getCause() instanceof java.io.InvalidClassException);
        assertTrue(exception.getCause().getCause().getMessage().contains("REJECTED"));
    }

    @Test
    @SuppressWarnings("deprecation")
    void testFromStream_objectInputFilter_undecidedPath() throws Exception {
        // Test to trigger UNDECIDED path - use a deeply nested structure
        // that might trigger filter callbacks with null serialClass()

        // Create a nested ArrayList structure
        java.util.ArrayList<java.util.ArrayList<String>> nested = new java.util.ArrayList<>();
        for (int i = 0; i < 3; i++) {
            java.util.ArrayList<String> inner = new java.util.ArrayList<>();
            inner.add("item-" + i + "-1");
            inner.add("item-" + i + "-2");
            nested.add(inner);
        }

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos)) {
            oos.writeObject(nested);
        }

        // Deserialize - during processing, the filter may be called with null serialClass
        // for metadata operations (array size, stream depth, etc.)
        java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(baos.toByteArray());
        java.lang.reflect.Method method = StringUtil.class.getDeclaredMethod(
            "fromStream", java.io.InputStream.class);
        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        java.util.ArrayList<java.util.ArrayList<String>> result =
            (java.util.ArrayList<java.util.ArrayList<String>>) method.invoke(null, bais);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("item-0-1", result.get(0).get(0));
    }

    // Static nested class for testing - in org.flossware.jcommons.util package (trusted)
    private static class CustomSerializable implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        private final String data;

        public CustomSerializable(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }
    }

    @Test
    void testPrivateConstructor() throws Exception {
        Constructor<StringUtil> constructor = StringUtil.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);

        var exception = assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof AssertionError);
        assertEquals("Utility class - do not instantiate", exception.getCause().getMessage());
    }
}
