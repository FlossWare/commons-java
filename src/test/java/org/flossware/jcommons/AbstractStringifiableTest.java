package org.flossware.jcommons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractStringifiableTest {

    private static class TestStringifiable extends AbstractStringifiable {
        private final String value;

        TestStringifiable(String value) {
            this.value = value;
        }

        @Override
        public StringBuilder toStringBuilder(StringBuilder sb, String prefix) {
            return appendLine(sb, prefix, "TestStringifiable[", value, "]");
        }
    }

    @Test
    void testAppendLine_singlePart() {
        TestStringifiable test = new TestStringifiable("test");
        StringBuilder sb = new StringBuilder();
        test.appendLine(sb, "Hello");

        String result = sb.toString();
        assertEquals("Hello" + System.lineSeparator(), result);
    }

    @Test
    void testAppendLine_multipleParts() {
        TestStringifiable test = new TestStringifiable("test");
        StringBuilder sb = new StringBuilder();
        test.appendLine(sb, "Part1", "Part2", "Part3");

        String result = sb.toString();
        assertEquals("Part1Part2Part3" + System.lineSeparator(), result);
    }

    @Test
    void testToStringBuilder_withStringBuilder() {
        TestStringifiable test = new TestStringifiable("value1");
        StringBuilder result = test.toStringBuilder(new StringBuilder());

        String str = result.toString();
        assertTrue(str.contains("TestStringifiable"));
        assertTrue(str.contains("value1"));
        assertTrue(str.contains(System.lineSeparator()));
    }

    @Test
    void testToStringBuilder_withPrefix() {
        TestStringifiable test = new TestStringifiable("value1");
        StringBuilder result = test.toStringBuilder("PREFIX:");

        String str = result.toString();
        assertTrue(str.contains("PREFIX:"));
        assertTrue(str.contains("TestStringifiable"));
        assertTrue(str.contains("value1"));
    }

    @Test
    void testToStringBuilder_emptyPrefix() {
        TestStringifiable test = new TestStringifiable("value1");
        StringBuilder result = test.toStringBuilder("");

        String str = result.toString();
        assertTrue(str.contains("TestStringifiable"));
        assertFalse(str.startsWith("PREFIX"));
    }

    @Test
    void testToStringBuilder_reuseStringBuilder() {
        TestStringifiable test = new TestStringifiable("value1");
        StringBuilder sb = new StringBuilder("existing");
        StringBuilder result = test.toStringBuilder(sb, "");

        assertSame(sb, result);
        assertTrue(result.toString().contains("existing"));
        assertTrue(result.toString().contains("TestStringifiable"));
    }

    @Test
    void testToString() {
        TestStringifiable test = new TestStringifiable("testValue");
        String result = test.toString();

        assertTrue(result.contains("TestStringifiable"));
        assertTrue(result.contains("testValue"));
        assertTrue(result.contains(System.lineSeparator()));
    }

    @Test
    void testToString_multipleValues() {
        TestStringifiable test1 = new TestStringifiable("value1");
        TestStringifiable test2 = new TestStringifiable("value2");

        assertNotEquals(test1.toString(), test2.toString());
        assertTrue(test1.toString().contains("value1"));
        assertTrue(test2.toString().contains("value2"));
    }

    @Test
    void testAppendLine_emptyParts() {
        TestStringifiable test = new TestStringifiable("test");
        StringBuilder sb = new StringBuilder();
        test.appendLine(sb, "", "", "");

        String result = sb.toString();
        assertEquals(System.lineSeparator(), result);
    }

    @Test
    void testLineSeparatorInOutput() {
        TestStringifiable test = new TestStringifiable("value");
        String result = test.toString();

        assertTrue(result.endsWith(System.lineSeparator()));
    }
}
