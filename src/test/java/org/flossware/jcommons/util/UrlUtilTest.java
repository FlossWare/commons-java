package org.flossware.jcommons.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void testPrivateConstructor() throws Exception {
        Constructor<UrlUtil> constructor = UrlUtil.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
