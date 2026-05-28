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
import org.mockito.MockedStatic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

class UrlUtilTest {

    @Test
    void testCreateUrl_withValidUrl() {
        URL url = UrlUtil.createUrl("http://example.com/path");
        assertNotNull(url);
        assertEquals("http", url.getProtocol());
        assertEquals("example.com", url.getHost());
    }

    @Test
    void testCreateUrl_withInvalidUrl() {
        assertThrows(IllegalArgumentException.class, () ->
            UrlUtil.createUrl("not a valid url"));
    }

    @Test
    void testCreateUrl_withHttps() {
        URL url = UrlUtil.createUrl("https://secure.example.com");
        assertEquals("https", url.getProtocol());
        assertEquals("secure.example.com", url.getHost());
    }

    @Test
    void testAsProtocolAndHost() throws Exception {
        URL url = new URL("http://example.com:8080/path/to/resource");
        String result = UrlUtil.asProtocolAndHost(url);
        assertEquals("http://example.com", result);
    }

    @Test
    void testAsProtocolAndHost_withHttps() throws Exception {
        URL url = new URL("https://secure.example.com/api");
        String result = UrlUtil.asProtocolAndHost(url);
        assertEquals("https://secure.example.com", result);
    }

    @Test
    void testComputeHostUrlAsString() {
        String result = UrlUtil.computeHostUrlAsString("http://example.com/path/to/resource");
        assertEquals("http://example.com", result);
    }

    @Test
    void testComputeHostUrlAsString_withPort() {
        String result = UrlUtil.computeHostUrlAsString("http://example.com:8080/path");
        assertEquals("http://example.com", result);
    }

    @Test
    void testComputeHostUrl() {
        URL url = UrlUtil.computeHostUrl("http://example.com/path/to/resource");
        assertNotNull(url);
        assertEquals("http", url.getProtocol());
        assertEquals("example.com", url.getHost());
    }

    @Test
    void testComputeHostUrl_withInvalidUrl() {
        assertThrows(IllegalArgumentException.class, () ->
            UrlUtil.computeHostUrl("not a valid url"));
    }

    @Test
    void testProtocolSeparatorConstant() {
        assertEquals("://", UrlUtil.PROTOCOL_SEPARATOR);
    }

    @Test
    void testComputeHostUrl_withMalformedURLException() {
        // Use MockedStatic to force MalformedURLException and trigger UrlException
        try (MockedStatic<UrlUtil> mockedUtil = mockStatic(UrlUtil.class)) {
            // Mock computeHostUrlAsString to return an invalid URL string
            mockedUtil.when(() -> UrlUtil.computeHostUrlAsString(anyString()))
                      .thenReturn(":::invalid:::");

            // Keep the real implementation for computeHostUrl
            mockedUtil.when(() -> UrlUtil.computeHostUrl(anyString()))
                      .thenCallRealMethod();

            // This should trigger the MalformedURLException catch block -> UrlException
            assertThrows(UrlException.class, () ->
                UrlUtil.computeHostUrl("http://example.com"));
        }
    }

    @Test
    void testCreateUrl_withNull() {
        assertThrows(NullPointerException.class, () ->
            UrlUtil.createUrl(null));
    }

    @Test
    void testAsProtocolAndHost_withNull() {
        assertThrows(NullPointerException.class, () ->
            UrlUtil.asProtocolAndHost(null));
    }

    @Test
    void testComputeHostUrlAsString_withNull() {
        assertThrows(NullPointerException.class, () ->
            UrlUtil.computeHostUrlAsString(null));
    }

    @Test
    void testComputeHostUrl_withNull() {
        assertThrows(NullPointerException.class, () ->
            UrlUtil.computeHostUrl(null));
    }

    @Test
    void testPrivateConstructor() throws Exception {
        Constructor<UrlUtil> constructor = UrlUtil.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);

        var exception = assertThrows(java.lang.reflect.InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof AssertionError);
        assertEquals("Utility class - do not instantiate", exception.getCause().getMessage());
    }
}
