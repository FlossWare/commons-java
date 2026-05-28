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

/**
 * JCommons: Common Java utility library.
 *
 * <p>Provides utility classes for working with:
 * <ul>
 *   <li>Strings, arrays, objects, files, and I/O operations</li>
 *   <li>SOAP web services (Apache CXF integration)</li>
 *   <li>Logging and property file handling</li>
 *   <li>Class and method introspection</li>
 * </ul>
 *
 * @since 1.32
 */
module org.flossware.commons {
    // Java platform modules
    requires java.logging;
    requires java.xml;

    // External dependencies
    requires org.apache.commons.lang3;
    requires jakarta.xml.ws;

    // Apache CXF modules (automatic modules)
    requires org.apache.cxf.core;
    requires org.apache.cxf.frontend.jaxws;
    requires org.apache.cxf.frontend.simple;

    // Export public API packages
    exports org.flossware.commons;
    exports org.flossware.commons.io;
    exports org.flossware.commons.soap;
    exports org.flossware.commons.util;
}
