package org.flossware.jcommons.soap;

import jakarta.xml.ws.Service;

/**
 * Record type to hold a SOAP service and the port class type.
 *
 * @param <T> the port type
 * @param service the SOAP service instance
 * @param portType the port class type
 * @author sfloess
 */
public record SoapRecord<T>(Service service, Class<T> portType) {
}
