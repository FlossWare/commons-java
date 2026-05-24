package org.flossware.jcommons.soap;

import jakarta.xml.ws.Service;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SoapRecordTest {

    interface TestPort {
        void testMethod();
    }

    @Test
    void testSoapRecordCreation() {
        Service mockService = mock(Service.class);
        Class<TestPort> portType = TestPort.class;

        SoapRecord<TestPort> record = new SoapRecord<>(mockService, portType);

        assertNotNull(record);
        assertEquals(mockService, record.service());
        assertEquals(portType, record.portType());
    }

    @Test
    void testSoapRecordEquality() {
        Service mockService = mock(Service.class);
        Class<TestPort> portType = TestPort.class;

        SoapRecord<TestPort> record1 = new SoapRecord<>(mockService, portType);
        SoapRecord<TestPort> record2 = new SoapRecord<>(mockService, portType);

        assertEquals(record1, record2);
        assertEquals(record1.hashCode(), record2.hashCode());
    }

    @Test
    void testSoapRecordInequality() {
        Service mockService1 = mock(Service.class);
        Service mockService2 = mock(Service.class);
        Class<TestPort> portType = TestPort.class;

        SoapRecord<TestPort> record1 = new SoapRecord<>(mockService1, portType);
        SoapRecord<TestPort> record2 = new SoapRecord<>(mockService2, portType);

        assertNotEquals(record1, record2);
    }

    @Test
    void testSoapRecordToString() {
        Service mockService = mock(Service.class);
        Class<TestPort> portType = TestPort.class;

        SoapRecord<TestPort> record = new SoapRecord<>(mockService, portType);

        String result = record.toString();
        assertNotNull(result);
        assertTrue(result.contains("SoapRecord"));
    }

    @Test
    void testSoapRecordWithNullService() {
        Class<TestPort> portType = TestPort.class;

        SoapRecord<TestPort> record = new SoapRecord<>(null, portType);

        assertNull(record.service());
        assertEquals(portType, record.portType());
    }

    @Test
    void testSoapRecordWithNullPortType() {
        Service mockService = mock(Service.class);

        SoapRecord<TestPort> record = new SoapRecord<>(mockService, null);

        assertEquals(mockService, record.service());
        assertNull(record.portType());
    }
}
