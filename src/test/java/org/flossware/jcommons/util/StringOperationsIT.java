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

import java.io.Serializable;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for string and URL operations.
 * Tests realistic workflows combining StringUtil, UrlUtil, and ArrayUtil.
 */
class StringOperationsIT {

    @Test
    void testUrlWorkflow() throws Exception {
        // Build URL and extract protocol/host
        String rawUrl = "https://example.com/api/v1/users";

        URL url = UrlUtil.createUrl(rawUrl);
        String protocolAndHost = UrlUtil.asProtocolAndHost(url);

        assertTrue(protocolAndHost.contains("https"));
        assertTrue(protocolAndHost.contains("example.com"));
    }

    @Test
    void testStringValidationChain() {
        String userInput = "  john.doe@example.com  ";

        // Validation chain
        String validated = StringUtil.requireNonBlank(userInput);
        validated = validated.trim();
        validated = validated.toLowerCase();

        assertEquals("john.doe@example.com", validated);
        assertFalse(validated.isEmpty());
    }

    @Test
    void testStringConcatenationWorkflow() {
        // Build complex string using concat methods
        Object[] parts = {"first", "second", "third"};

        String result = StringUtil.concatWithSeparator(", ", parts);
        assertEquals("first, second, third", result);

        // With separator at end
        String resultWithEnd = StringUtil.concatWithSeparator(true, ", ", parts);
        assertEquals("first, second, third, ", resultWithEnd);
    }

    @Test
    void testMultipleUtilityIntegration() {
        // Complex workflow using multiple utilities
        Object[] names = {"Alice", "Bob", "Charlie"};

        // Validate and concatenate
        assertNotNull(names);
        assertTrue(names.length > 0);

        String allNames = StringUtil.concatWithSeparator(" | ", names);

        assertEquals("Alice | Bob | Charlie", allNames);
    }

    @Test
    void testSerializationWorkflow() {
        // Test serializable record
        record TestData(String name, int value) implements Serializable {}

        TestData original = new TestData("test", 42);

        // Serialize to string
        String serialized = StringUtil.toString(original);
        assertNotNull(serialized);
        assertFalse(serialized.isEmpty());

        // Deserialize back
        TestData deserialized = StringUtil.fromString(serialized);
        assertEquals(original, deserialized);
    }

    @Test
    void testBase64EncodingWorkflow() {
        // Test base64 encoding with serialization
        record SimpleData(String value) implements Serializable {}
        SimpleData original = new SimpleData("test");

        String encoded = StringUtil.toString(original);
        assertNotNull(encoded);

        // Deserialize back
        SimpleData decoded = StringUtil.fromString(encoded);
        assertEquals(original, decoded);
    }

    @Test
    void testCompressionWorkflow() {
        record LargeData(String data) implements Serializable {}

        // Create large data
        String largeString = "A".repeat(1000);
        LargeData data = new LargeData(largeString);

        // Compress
        String compressed = StringUtil.toCompressedString(data);
        assertNotNull(compressed);

        // Compressed should be smaller (base64 overhead but gzip compression)
        // Just verify it works, not necessarily smaller for small data
        LargeData decompressed = StringUtil.fromCompressedString(compressed);
        assertEquals(data, decompressed);
    }

    @Test
    void testStringBuilderWorkflow() {
        // Complex StringBuilder workflow
        StringBuilder sb = new StringBuilder();

        // Build using concat
        StringUtil.concat(sb, "Hello");
        StringUtil.concat(sb, " ");
        StringUtil.concat(sb, "World");

        String result = sb.toString();
        assertEquals("Hello World", result);
    }
}
