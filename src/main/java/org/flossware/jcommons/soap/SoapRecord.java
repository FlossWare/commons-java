package org.flossware.jcommons.soap;

import jakarta.xml.ws.Service;
import java.util.Objects;

/**
 * Record type to hold a SOAP service and the port class type.
 *
 * <p>Both parameters are validated for null in the compact constructor.
 *
 * @param <T> the port type
 * @param service the SOAP service instance (must not be null)
 * @param portType the port class type (must not be null)
 * @author sfloess
 */
public record SoapRecord<T>(Service service, Class<T> portType) {
    /**
     * Compact constructor that validates parameters.
     */
    public SoapRecord {
        Objects.requireNonNull(service, "Service must not be null");
        Objects.requireNonNull(portType, "Port type must not be null");
    }
}
