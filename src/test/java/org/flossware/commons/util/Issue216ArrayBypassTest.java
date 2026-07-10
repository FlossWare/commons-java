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

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Security tests for Issue #216 - Array Deserialization Bypass.
 * Verifies that arrays containing dangerous classes are properly rejected.
 */
class Issue216ArrayBypassTest {

    @Test
    @SuppressWarnings("deprecation")
    void testRejectHashMapArray() {
        // HashMap is in DANGEROUS_CLASSES, so HashMap[] should be REJECTED
        HashMap<String, String> map = new HashMap<>();
        map.put("exploit", "payload");
        HashMap<String, String>[] mapArray = new HashMap[] { map };

        String serialized = StringUtil.toCompressedString(mapArray);

        // Should throw RuntimeException because HashMap[] contains dangerous component
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            StringUtil.fromCompressedString(serialized));

        assertTrue(exception.getMessage().contains("Failed to deserialize"),
            "HashMap[] should be rejected - got: " + exception.getMessage());
    }

    @Test
    @SuppressWarnings("deprecation")
    void testRejectPriorityQueueArray() {
        // PriorityQueue is in DANGEROUS_CLASSES, so PriorityQueue[] should be REJECTED
        PriorityQueue<String> queue = new PriorityQueue<>();
        queue.add("test");
        PriorityQueue<String>[] queueArray = new PriorityQueue[] { queue };

        String serialized = StringUtil.toCompressedString(queueArray);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            StringUtil.fromCompressedString(serialized));

        assertTrue(exception.getMessage().contains("Failed to deserialize"),
            "PriorityQueue[] should be rejected - got: " + exception.getMessage());
    }

    @Test
    @SuppressWarnings("deprecation")
    void testRejectTreeSetArray() {
        // TreeSet is in DANGEROUS_CLASSES, so TreeSet[] should be REJECTED
        TreeSet<String> set = new TreeSet<>();
        set.add("test");
        TreeSet<String>[] setArray = new TreeSet[] { set };

        String serialized = StringUtil.toCompressedString(setArray);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            StringUtil.fromCompressedString(serialized));

        assertTrue(exception.getMessage().contains("Failed to deserialize"),
            "TreeSet[] should be rejected - got: " + exception.getMessage());
    }

    @Test
    @SuppressWarnings("deprecation")
    void testRejectMultiDimensionalHashMapArray() {
        // HashMap[][] should also be REJECTED (multi-dimensional array of dangerous class)
        HashMap<String, String> map = new HashMap<>();
        map.put("nested", "exploit");
        HashMap<String, String>[][] nestedArray = new HashMap[][] { { map } };

        String serialized = StringUtil.toCompressedString(nestedArray);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            StringUtil.fromCompressedString(serialized));

        assertTrue(exception.getMessage().contains("Failed to deserialize"),
            "HashMap[][] should be rejected - got: " + exception.getMessage());
    }

    @Test
    @SuppressWarnings("deprecation")
    void testAllowStringArray() {
        // String[] should be ALLOWED (java.lang.String is safe)
        String[] stringArray = new String[] { "safe", "content", "allowed" };

        String serialized = StringUtil.toCompressedString(stringArray);
        String[] deserialized = StringUtil.fromCompressedString(serialized);

        assertNotNull(deserialized, "String[] should be allowed");
        assertEquals(3, deserialized.length);
        assertEquals("safe", deserialized[0]);
        assertEquals("content", deserialized[1]);
        assertEquals("allowed", deserialized[2]);
    }

    @Test
    @SuppressWarnings("deprecation")
    void testAllowPrimitiveIntArray() {
        // int[] should be ALLOWED (primitive arrays are inherently safe)
        int[] intArray = new int[] { 1, 2, 3, 4, 5 };

        String serialized = StringUtil.toCompressedString(intArray);
        int[] deserialized = StringUtil.fromCompressedString(serialized);

        assertNotNull(deserialized, "int[] should be allowed");
        assertEquals(5, deserialized.length);
        assertEquals(1, deserialized[0]);
        assertEquals(3, deserialized[2]);
        assertEquals(5, deserialized[4]);
    }

    @Test
    @SuppressWarnings("deprecation")
    void testAllowPrimitiveBooleanArray() {
        // boolean[] should be ALLOWED (primitive arrays are safe)
        boolean[] boolArray = new boolean[] { true, false, true };

        String serialized = StringUtil.toCompressedString(boolArray);
        boolean[] deserialized = StringUtil.fromCompressedString(serialized);

        assertNotNull(deserialized, "boolean[] should be allowed");
        assertEquals(3, deserialized.length);
        assertTrue(deserialized[0]);
        assertFalse(deserialized[1]);
        assertTrue(deserialized[2]);
    }

    @Test
    @SuppressWarnings("deprecation")
    void testAllowMultiDimensionalStringArray() {
        // String[][] should be ALLOWED (component type is safe)
        String[][] stringArray = new String[][] {
            { "row1", "col1" },
            { "row2", "col2" }
        };

        String serialized = StringUtil.toCompressedString(stringArray);
        String[][] deserialized = StringUtil.fromCompressedString(serialized);

        assertNotNull(deserialized, "String[][] should be allowed");
        assertEquals(2, deserialized.length);
        assertEquals("row1", deserialized[0][0]);
        assertEquals("col2", deserialized[1][1]);
    }
}
