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

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggerUtilTest {

    private final Logger logger = Logger.getLogger(LoggerUtilTest.class.getName());

    @Test
    void testLog_withMessage() {
        assertDoesNotThrow(() ->
            LoggerUtil.log(logger, Level.INFO, "Test message"));
    }

    @Test
    void testLog_withMessageAndVarArgs() {
        assertDoesNotThrow(() ->
            LoggerUtil.log(logger, Level.INFO, "Test {0} with {1}", "message", "args"));
    }

    @Test
    void testLog_withThrowable() {
        Exception ex = new RuntimeException("test");
        assertDoesNotThrow(() ->
            LoggerUtil.log(logger, Level.WARNING, ex, "Exception occurred"));
    }

    @Test
    void testLog_withThrowableAndVarArgs() {
        Exception ex = new RuntimeException("test");
        assertDoesNotThrow(() ->
            LoggerUtil.log(logger, Level.WARNING, ex, "Exception {0}", "occurred"));
    }

    @Test
    void testLogAndReturn_returnsValue() {
        String value = "test value";
        String result = LoggerUtil.logAndReturn(logger, Level.INFO, "Returning {0}", value);
        assertEquals(value, result);
    }

    @Test
    void testLogAndReturn_withVarArgs() {
        String value1 = "first";
        String value2 = "second";
        String result = LoggerUtil.logAndReturn(logger, Level.INFO, "Values: {0} {1}", value1, value2);
        assertEquals(value1, result);
    }

    @Test
    void testLogAndReturnByIndex() {
        String value1 = "first";
        String value2 = "second";
        String value3 = "third";

        String result = LoggerUtil.logAndReturnByIndex(logger, Level.INFO, "Values", 1, value1, value2, value3);
        assertEquals(value2, result);
    }

    @Test
    void testLogAndReturnByIndex_zeroIndex() {
        String value1 = "first";
        String value2 = "second";

        String result = LoggerUtil.logAndReturnByIndex(logger, Level.INFO, "Values", 0, value1, value2);
        assertEquals(value1, result);
    }

    @Test
    void testLogAndReturn_withNullValue() {
        String result = LoggerUtil.logAndReturn(logger, Level.INFO, "Null value", (String) null);
        assertNull(result);
    }

    @Test
    void testLogAndReturnByIndex_negativeIndex() {
        assertThrows(IllegalArgumentException.class, () ->
            LoggerUtil.logAndReturnByIndex(logger, Level.INFO, "Test", -1, "value1", "value2"));
    }

    @Test
    void testLogAndReturnByIndex_indexTooLarge() {
        assertThrows(IllegalArgumentException.class, () ->
            LoggerUtil.logAndReturnByIndex(logger, Level.INFO, "Test", 5, "value1", "value2"));
    }

    @Test
    void testLogAndReturnByIndex_indexEqualsLength() {
        assertThrows(IllegalArgumentException.class, () ->
            LoggerUtil.logAndReturnByIndex(logger, Level.INFO, "Test", 2, "value1", "value2"));
    }

    @Test
    void testPrivateConstructor() throws Exception {
        java.lang.reflect.Constructor<LoggerUtil> constructor = LoggerUtil.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);

        var exception = assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof AssertionError);
        assertEquals("Utility class - do not instantiate", exception.getCause().getMessage());
    }
}
