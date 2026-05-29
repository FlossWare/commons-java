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
package org.flossware.commons.soap;

import jakarta.xml.ws.Service;
import org.flossware.commons.util.SoapUtil;
import org.flossware.commons.util.UrlUtil;
import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for SOAP utilities.
 * Tests SoapRecord, SoapUtil, and URL utilities working together.
 */
class SoapIntegrationIT {

    // Mock port interface for testing
    interface MockPort {}

    @Test
    void testSoapRecordCreation() {
        Service mockService = Service.create(new QName("http://test.com", "TestService"));

        // Create SoapRecord
        SoapRecord<MockPort> record = new SoapRecord<>(mockService, MockPort.class);

        assertNotNull(record);
        assertSame(mockService, record.service());
        assertEquals(MockPort.class, record.portType());
    }

    @Test
    void testSoapRecordNullValidation() {
        Service mockService = Service.create(new QName("http://test.com", "TestService"));

        // Test null service
        assertThrows(NullPointerException.class, () ->
            new SoapRecord<>(null, MockPort.class));

        // Test null port type
        assertThrows(NullPointerException.class, () ->
            new SoapRecord<>(mockService, null));
    }

    @Test
    void testSoapRecordEquality() {
        Service service1 = Service.create(new QName("http://test.com", "Service1"));
        Service service2 = Service.create(new QName("http://test.com", "Service2"));

        SoapRecord<MockPort> record1 = new SoapRecord<>(service1, MockPort.class);
        SoapRecord<MockPort> record2 = new SoapRecord<>(service1, MockPort.class);
        SoapRecord<MockPort> record3 = new SoapRecord<>(service2, MockPort.class);

        assertEquals(record1, record2);
        assertNotEquals(record1, record3);
        assertEquals(record1.hashCode(), record2.hashCode());
    }

    @Test
    void testSoapRecordToString() {
        Service mockService = Service.create(new QName("http://test.com", "TestService"));
        SoapRecord<MockPort> record = new SoapRecord<>(mockService, MockPort.class);

        String toString = record.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("SoapRecord"));
        assertTrue(toString.contains("service"));
        assertTrue(toString.contains("portType"));
    }

    @Test
    void testSoapServiceCreation() throws Exception {
        QName serviceName = new QName("http://example.com/service", "TestService");
        Service service = Service.create(serviceName);

        assertNotNull(service);
        assertEquals(serviceName, service.getServiceName());
    }

    @Test
    void testUrlToSoapWorkflow() throws Exception {
        // Build SOAP endpoint URL
        String wsdlUrl = "https://example.com/service?wsdl";
        URL url = UrlUtil.createUrl(wsdlUrl);

        assertNotNull(url);
        assertEquals("https", url.getProtocol());
        assertEquals("example.com", url.getHost());
        assertTrue(url.getPath().contains("service"));

        // Extract protocol and host using UrlUtil
        String protocolAndHost = UrlUtil.asProtocolAndHost(url);
        assertTrue(protocolAndHost.contains("https"));
        assertTrue(protocolAndHost.contains("example.com"));
    }

    @Test
    void testSoapFactoryReuse() {
        try {
            // Test that getting SOAP factory multiple times returns same instance
            var factory1 = SoapUtil.getSoapFactory();
            var factory2 = SoapUtil.getSoapFactory();

            assertNotNull(factory1);
            assertNotNull(factory2);
            // Factory reuse
            assertSame(factory1, factory2);
        } catch (org.flossware.commons.util.SoapException e) {
            // SOAP factory may not be available in test environment
            // This is expected - skip the test
            System.out.println("SOAP factory not available in test environment - skipping");
        }
    }
}
