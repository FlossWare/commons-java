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
 * I/O exception classes for the jcommons library.
 *
 * <p>This package provides specialized runtime exceptions for I/O operations:
 *
 * <ul>
 *   <li>{@link org.flossware.jcommons.io.JCommonsIOException} - General I/O exception wrapper</li>
 *   <li>{@link org.flossware.jcommons.io.FileException} - File operation exceptions</li>
 * </ul>
 *
 * <p>All exceptions in this package are unchecked (extend RuntimeException) to avoid
 * forcing exception handling in client code. They wrap checked exceptions from java.io
 * and provide additional context.
 *
 * @author Scot P. Floess
 */
package org.flossware.commons.io;
