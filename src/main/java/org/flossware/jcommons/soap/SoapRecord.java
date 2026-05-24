package org.flossware.jcommons.soap;

import jakarta.xml.ws.Service;
import java.util.Objects;

/**
 * Record type to hold a SOAP service and the port class type.
 *
 * @param <T> the port type
 * @param service the SOAP service instance
 * @param portType the port class type
 * @throws NullPointerException if service or portType is null
 * @author sfloess
 */
public record SoapRecord<T>(Service service, Class<T> portType) {
    /**
     * Compact constructor that validates parameters.
     */
    public SoapRecord {
        Objects.requireNonNull(service, "Service cannot be null");
        Objects.requireNonNull(portType, "Port type cannot be null");
    }
}
