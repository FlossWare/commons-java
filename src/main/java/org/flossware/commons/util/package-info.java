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
 * Utility classes for common Java operations.
 *
 * <p>This package provides static utility methods for:
 *
 * <h2>Core Utilities</h2>
 * <ul>
 *   <li>{@link org.flossware.jcommons.util.ArrayUtil} - Array validation and manipulation</li>
 *   <li>{@link org.flossware.jcommons.util.ClassUtil} - Class introspection and loading</li>
 *   <li>{@link org.flossware.jcommons.util.LoggerUtil} - Enhanced logging with format arguments and log-and-return</li>
 *   <li>{@link org.flossware.jcommons.util.ObjectUtil} - Object comparison and validation</li>
 *   <li>{@link org.flossware.jcommons.util.StringUtil} - String validation, encoding, and serialization</li>
 * </ul>
 *
 * <h2>I/O Utilities</h2>
 * <ul>
 *   <li>{@link org.flossware.jcommons.util.FileUtil} - File operations and validation</li>
 *   <li>{@link org.flossware.jcommons.util.IOUtils} - InputStream/OutputStream utilities</li>
 *   <li>{@link org.flossware.jcommons.util.PropertyUtil} - Properties file loading and validation</li>
 * </ul>
 *
 * <h2>Web and SOAP Utilities</h2>
 * <ul>
 *   <li>{@link org.flossware.jcommons.util.UrlUtil} - URL parsing and validation</li>
 *   <li>{@link org.flossware.jcommons.util.SoapUtil} - SOAP web service client utilities</li>
 *   <li>{@link org.flossware.jcommons.util.MethodUtil} - Method introspection for annotations</li>
 * </ul>
 *
 * <p>All classes in this package are final utility classes with private constructors,
 * following the static utility class pattern. They cannot be instantiated.
 *
 * @author Scot P. Floess
 */
package org.flossware.commons.util;
