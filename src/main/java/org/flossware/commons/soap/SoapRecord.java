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
import java.util.Objects;

/**
 * Record type to hold a SOAP service and the port class type.
 *
 * <p>Both parameters are validated for null in the compact constructor.
 *
 * @param <T> the port type
 * @param service the SOAP service instance (must not be null)
 * @param portType the port class type (must not be null)
 * @author Scot P. Floess
 */
public record SoapRecord<T>(Service service, Class<T> portType) {
    /**
     * Compact constructor that validates parameters.
     *
     * @param service the SOAP service instance (must not be null)
     * @param portType the port class type (must not be null)
     */
    public SoapRecord {
        Objects.requireNonNull(service, "Service must not be null");
        Objects.requireNonNull(portType, "Port type must not be null");
    }
}
