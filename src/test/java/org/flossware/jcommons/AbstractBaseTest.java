package org.flossware.jcommons;

import org.junit.jupiter.api.Test;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbstractBaseTest {

    private static class TestAbstractBase extends AbstractBase {
        public void testLog() {
            log(Level.INFO, "Test message");
        }

        public void testLogWithArgs() {
            log(Level.INFO, "Test {0} with {1}", "message", "args");
        }

        public void testLogWithThrowable() {
            log(Level.WARNING, new RuntimeException("test"), "Exception occurred");
        }

        public void testLogWithThrowableAndArgs() {
            log(Level.WARNING, new RuntimeException("test"), "Exception {0}", "occurred");
        }

        public String testLogAndReturn() {
            return logAndReturn(Level.INFO, "Returning value", "test");
        }

        public String testLogAndReturnWithArgs() {
            return logAndReturn(Level.INFO, "Values", "first", "second");
        }

        public String testLogAndReturnByIndex() {
            return logAndReturnByIndex(Level.INFO, "Values", 1, "first", "second", "third");
        }

        public void testLogInfoMethod() {
            logInfo("Info message");
        }

        public void testLogInfoWithArgs() {
            logInfo("Info {0}", "message");
        }

        public void testLogWarningMethod() {
            logWarning("Warning message");
        }

        public void testLogWarningWithArgs() {
            logWarning("Warning {0}", "message");
        }

        public void testLogWarningWithThrowable() {
            logWarning(new RuntimeException("test"), "Warning with exception");
        }

        public void testLogWarningWithThrowableAndArgs() {
            logWarning(new RuntimeException("test"), "Warning {0}", "with exception");
        }

        public void testLogErrorMethod() {
            logError("Error message");
        }

        public void testLogErrorWithArgs() {
            logError("Error {0}", "message");
        }

        public void testLogErrorWithThrowable() {
            logError(new RuntimeException("test"), "Error with exception");
        }

        public void testLogErrorWithThrowableAndArgs() {
            logError(new RuntimeException("test"), "Error {0}", "with exception");
        }
    }

    @Test
    void testAbstractBase_hasLogger() {
        TestAbstractBase instance = new TestAbstractBase();
        assertNotNull(instance.getLogger());
    }

    @Test
    void testLog_simple() {
        TestAbstractBase instance = new TestAbstractBase();
        assertDoesNotThrow(instance::testLog);
    }

    @Test
    void testLog_withArgs() {
        TestAbstractBase instance = new TestAbstractBase();
        assertDoesNotThrow(instance::testLogWithArgs);
    }

    @Test
    void testLog_withThrowable() {
        TestAbstractBase instance = new TestAbstractBase();
        assertDoesNotThrow(instance::testLogWithThrowable);
    }

    @Test
    void testLog_withThrowableAndArgs() {
        TestAbstractBase instance = new TestAbstractBase();
        assertDoesNotThrow(instance::testLogWithThrowableAndArgs);
    }

    @Test
    void testLogAndReturn() {
        TestAbstractBase instance = new TestAbstractBase();
        String result = instance.testLogAndReturn();
        assertEquals("test", result);
    }

    @Test
    void testLogAndReturn_withArgs() {
        TestAbstractBase instance = new TestAbstractBase();
        String result = instance.testLogAndReturnWithArgs();
        assertEquals("first", result);
    }

    @Test
    void testLogAndReturnByIndex() {
        TestAbstractBase instance = new TestAbstractBase();
        String result = instance.testLogAndReturnByIndex();
        assertEquals("second", result);
    }

    @Test
    void testLogger_hasCorrectName() {
        TestAbstractBase instance = new TestAbstractBase();
        assertTrue(instance.getLogger().getName().contains("TestAbstractBase"));
    }

    @Test
    void testLogInfo() {
        TestAbstractBase instance = new TestAbstractBase();
        assertDoesNotThrow(instance::testLogInfoMethod);
    }

    @Test
    void testLogInfo_withArgs() {
        TestAbstractBase instance = new TestAbstractBase();
        assertDoesNotThrow(instance::testLogInfoWithArgs);
    }

    @Test
    void testLogWarning() {
        TestAbstractBase instance = new TestAbstractBase();
        assertDoesNotThrow(instance::testLogWarningMethod);
    }

    @Test
    void testLogWarning_withArgs() {
        TestAbstractBase instance = new TestAbstractBase();
        assertDoesNotThrow(instance::testLogWarningWithArgs);
    }

    @Test
    void testLogWarning_withThrowable() {
        TestAbstractBase instance = new TestAbstractBase();
        assertDoesNotThrow(instance::testLogWarningWithThrowable);
    }

    @Test
    void testLogWarning_withThrowableAndArgs() {
        TestAbstractBase instance = new TestAbstractBase();
        assertDoesNotThrow(instance::testLogWarningWithThrowableAndArgs);
    }

    @Test
    void testLogError() {
        TestAbstractBase instance = new TestAbstractBase();
        assertDoesNotThrow(instance::testLogErrorMethod);
    }

    @Test
    void testLogError_withArgs() {
        TestAbstractBase instance = new TestAbstractBase();
        assertDoesNotThrow(instance::testLogErrorWithArgs);
    }

    @Test
    void testLogError_withThrowable() {
        TestAbstractBase instance = new TestAbstractBase();
        assertDoesNotThrow(instance::testLogErrorWithThrowable);
    }

    @Test
    void testLogError_withThrowableAndArgs() {
        TestAbstractBase instance = new TestAbstractBase();
        assertDoesNotThrow(instance::testLogErrorWithThrowableAndArgs);
    }
}
