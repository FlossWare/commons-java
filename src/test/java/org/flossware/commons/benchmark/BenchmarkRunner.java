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

package org.flossware.commons.benchmark;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Entry point for running all JMH benchmarks.
 *
 * <p>This class can be executed directly or invoked via the Maven benchmarks profile:
 * {@code mvn test -Pbenchmarks}
 *
 * @author Scot P. Floess
 */
public final class BenchmarkRunner {

    private BenchmarkRunner() {
        throw new AssertionError("Utility class - do not instantiate");
    }

    /**
     * Runs all benchmarks in the org.flossware.commons.benchmark package.
     *
     * @param args command line arguments (unused)
     * @throws Exception if benchmark execution fails
     */
    public static void main(final String[] args) throws Exception {
        final Options options = new OptionsBuilder()
            .include("org\\.flossware\\.commons\\.benchmark\\..*")
            .resultFormat(ResultFormatType.TEXT)
            .build();

        new Runner(options).run();
    }
}
