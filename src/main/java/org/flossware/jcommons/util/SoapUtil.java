package org.flossware.jcommons.util;

import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPFactory;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceClient;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.headers.Header;

/**
 * Utility class for Apache CXF SOAP service operations.
 * Provides methods for configuring SOAP endpoints, headers, and QName computation.
 *
 * @author sfloess
 */
public final class SoapUtil {
    /**
     * Our logger.
     */
    private static final Logger logger = Logger.getLogger(SoapUtil.class.getName());

    private SoapUtil() {
        throw new AssertionError("Utility class - do not instantiate");
    }

    /**
     * Gets a new SOAP factory instance for creating SOAP elements.
     * Creates a new instance on each call to ensure thread safety.
     *
     * @return a new SOAPFactory instance
     * @throws SoapException if the SOAP factory could not be instantiated
     */
    public static SOAPFactory getSoapFactory() {
        try {
            return SOAPFactory.newInstance();
        } catch (final SOAPException soapException) {
            logger.log(Level.SEVERE, "Could not instantiate soap factory!", soapException);
            throw new SoapException("Could not instantiate soap factory!", soapException);
        }
    }

    /**
     * Sets a header on the SOAP service.
     *
     * @param service the SOAP service
     * @param name the header name
     * @param headerValue the header value
     *
     * @throws NullPointerException if service or name is null
     */
    public static void setHeader(final Service service, final String name, final Object headerValue) {
        Objects.requireNonNull(service, "Service cannot be null");
        Objects.requireNonNull(name, "Header name cannot be null");
        Objects.requireNonNull(headerValue, "Header value cannot be null");

        ClientProxy.getClient(service).getRequestContext().put(name, headerValue);
    }

    /**
     * Sets a header with QName on the SOAP service.
     *
     * @param service the SOAP service
     * @param qname the qualified name for the header
     * @param headerValue the header value
     *
     * @throws NullPointerException if service, qname, or headerValue is null
     */
    public static void setHeader(final Service service, final QName qname, final Object headerValue) {
        Objects.requireNonNull(service, "Service cannot be null");
        Objects.requireNonNull(qname, "QName cannot be null");
        Objects.requireNonNull(headerValue, "Header value cannot be null");

        setHeader(service, Header.HEADER_LIST, new Header(qname, headerValue));
    }

    /**
     * Sets multiple headers on the SOAP service.
     *
     * @param service the SOAP service
     * @param headers the headers to set
     *
     * @throws NullPointerException if service or headers is null
     */
    public static void setHeaders(final Service service, final Header... headers) {
        Objects.requireNonNull(service, "Service cannot be null");
        Objects.requireNonNull(headers, "Headers cannot be null");

        setHeader(service, Header.HEADER_LIST, Arrays.asList(headers));
    }

    /**
     * Sets the endpoint URL for a web service port.
     *
     * @param <T> the port type
     * @param port the web service port
     * @param url the endpoint URL
     * @return the port with the updated URL
     *
     * @throws NullPointerException if port or url is null
     * @throws IllegalArgumentException if url is blank
     */
    public static <T> T setUrl(final T port, final String url) {
        Objects.requireNonNull(port, "Port cannot be null");
        StringUtil.requireNonBlank(url, "URL cannot be blank");

        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);

        return port;
    }

    /**
     * Computes the QName from a WebServiceClient annotation.
     *
     * @param webServiceClient the WebServiceClient annotation
     * @return the computed QName
     *
     * @throws NullPointerException if webServiceClient is null
     */
    public static QName computeQName(final WebServiceClient webServiceClient) {
        Objects.requireNonNull(webServiceClient, "WebServiceClient annotation cannot be null");

        return new QName(webServiceClient.targetNamespace(), webServiceClient.name());
    }

    /**
     * Computes the QName from a Service class.
     *
     * @param klass the Service class
     * @return the computed QName
     *
     * @throws NullPointerException if klass is null
     * @throws IllegalArgumentException if the class is not annotated with @WebServiceClient
     */
    public static QName computeQName(final Class<? extends Service> klass) {
        Objects.requireNonNull(klass, "Service class cannot be null");

        WebServiceClient annotation = klass.getAnnotation(WebServiceClient.class);
        if (annotation == null) {
            throw new IllegalArgumentException("Class " + klass.getName() + " is not annotated with @WebServiceClient");
        }

        return computeQName(annotation);
    }

    /**
     * Computes the QName from a Service instance.
     *
     * @param service the Service instance
     * @return the computed QName
     *
     * @throws NullPointerException if service is null
     * @throws IllegalArgumentException if the service class is not annotated with @WebServiceClient
     */
    public static QName computeQName(final Service service) {
        Objects.requireNonNull(service, "Service cannot be null");

        return computeQName(service.getClass());
    }
}