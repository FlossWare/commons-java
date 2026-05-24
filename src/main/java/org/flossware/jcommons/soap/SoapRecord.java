package org.flossware.jcommons.soap;

import jakarta.xml.ws.Service;

/**
 * Record type to hold a SOAP service and the port class type.
 *
 * @param service the SOAP service instance
 * @param portType the port class type
 * @author sfloess
 */
public record SoapRecord(Service service, Class portType) {
}
