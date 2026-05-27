package org.flossware.jcommons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Stringifiable interface.
 * Verifies constants and interface contract.
 */
class StringifiableTest {

    @Test
    void testLineSeparatorPropertyConstant() {
        assertEquals("line.separator", Stringifiable.LINE_SEPARATOR_PROPERTY);
    }

    @Test
    void testLineSeparatorConstant() {
        assertNotNull(Stringifiable.LINE_SEPARATOR_STRING);
        assertEquals(System.getProperty(Stringifiable.LINE_SEPARATOR_PROPERTY),
                     Stringifiable.LINE_SEPARATOR_STRING);
    }

    @Test
    void testLineSeparatorIsNotEmpty() {
        assertNotNull(Stringifiable.LINE_SEPARATOR_STRING);
        assertFalse(Stringifiable.LINE_SEPARATOR_STRING.isEmpty());
        // On Unix: \n, On Windows: \r\n
        assertTrue(Stringifiable.LINE_SEPARATOR_STRING.length() >= 1);
    }

    @Test
    void testDefaultPrefixConstant() {
        assertEquals("    ", Stringifiable.DEFAULT_PREFIX);
        assertEquals(4, Stringifiable.DEFAULT_PREFIX.length());
    }

    @Test
    void testDefaultPrefixIsFourSpaces() {
        for (char c : Stringifiable.DEFAULT_PREFIX.toCharArray()) {
            assertEquals(' ', c, "Default prefix should only contain spaces");
        }
    }

    @Test
    void testInterfaceHasExpectedMethods() throws NoSuchMethodException {
        // Verify all three toStringBuilder overloads exist
        Stringifiable.class.getMethod("toStringBuilder",
            StringBuilder.class, String.class);
        Stringifiable.class.getMethod("toStringBuilder",
            StringBuilder.class);
        Stringifiable.class.getMethod("toStringBuilder",
            String.class);
    }

    @Test
    void testAbstractStringifiableImplementsInterface() {
        assertTrue(Stringifiable.class.isAssignableFrom(
            AbstractStringifiable.class));
    }

    @Test
    void testLineSeparatorFieldIsPublicStaticFinal() throws NoSuchFieldException {
        var lineSepField = Stringifiable.class.getField("LINE_SEPARATOR_STRING");

        assertTrue(java.lang.reflect.Modifier.isPublic(
            lineSepField.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isStatic(
            lineSepField.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isFinal(
            lineSepField.getModifiers()));
    }

    @Test
    void testDefaultPrefixFieldIsPublicStaticFinal() throws NoSuchFieldException {
        var prefixField = Stringifiable.class.getField("DEFAULT_PREFIX");

        assertTrue(java.lang.reflect.Modifier.isPublic(
            prefixField.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isStatic(
            prefixField.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isFinal(
            prefixField.getModifiers()));
    }

    @Test
    void testLineSeparatorPropertyFieldIsPublicStaticFinal() throws NoSuchFieldException {
        var propertyField = Stringifiable.class.getField("LINE_SEPARATOR_PROPERTY");

        assertTrue(java.lang.reflect.Modifier.isPublic(
            propertyField.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isStatic(
            propertyField.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isFinal(
            propertyField.getModifiers()));
    }

    @Test
    void testConstantFieldsAreStrings() throws NoSuchFieldException {
        assertEquals(String.class,
            Stringifiable.class.getField("LINE_SEPARATOR_STRING").getType());
        assertEquals(String.class,
            Stringifiable.class.getField("DEFAULT_PREFIX").getType());
        assertEquals(String.class,
            Stringifiable.class.getField("LINE_SEPARATOR_PROPERTY").getType());
    }

    @Test
    void testInterfaceMethodsReturnStringBuilder() throws NoSuchMethodException {
        var method1 = Stringifiable.class.getMethod("toStringBuilder",
            StringBuilder.class, String.class);
        var method2 = Stringifiable.class.getMethod("toStringBuilder",
            StringBuilder.class);
        var method3 = Stringifiable.class.getMethod("toStringBuilder",
            String.class);

        assertEquals(StringBuilder.class, method1.getReturnType());
        assertEquals(StringBuilder.class, method2.getReturnType());
        assertEquals(StringBuilder.class, method3.getReturnType());
    }
}
