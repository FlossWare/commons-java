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

import java.util.concurrent.TimeUnit;
import org.flossware.commons.util.ArrayUtil;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

/**
 * JMH benchmarks for {@link ArrayUtil} operations.
 *
 * <p>Run with: {@code mvn test -Pbenchmarks}
 *
 * @author Scot P. Floess
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class ArrayUtilBenchmark {

    private String[] singleElement;
    private String[] smallArray;
    private String[] mediumArray;
    private String[] largeArray;

    @Setup
    public void setUp() {
        singleElement = new String[]{"one"};
        smallArray = new String[]{"alpha", "beta", "gamma"};
        mediumArray = new String[20];
        for (int i = 0; i < mediumArray.length; i++) {
            mediumArray[i] = "element-" + i;
        }
        largeArray = new String[100];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = "item-" + i;
        }
    }

    // --- ensureArray with default minLength ---

    @Benchmark
    public void ensureArray_singleElement(Blackhole bh) {
        bh.consume(ArrayUtil.ensureArray(singleElement));
    }

    @Benchmark
    public void ensureArray_smallArray(Blackhole bh) {
        bh.consume(ArrayUtil.ensureArray(smallArray));
    }

    @Benchmark
    public void ensureArray_mediumArray(Blackhole bh) {
        bh.consume(ArrayUtil.ensureArray(mediumArray));
    }

    @Benchmark
    public void ensureArray_largeArray(Blackhole bh) {
        bh.consume(ArrayUtil.ensureArray(largeArray));
    }

    // --- ensureArray with explicit minLength ---

    @Benchmark
    public void ensureArray_withMinLength(Blackhole bh) {
        bh.consume(ArrayUtil.ensureArray(smallArray, 2));
    }

    @Benchmark
    public void ensureArray_withMinLengthAndMessage(Blackhole bh) {
        bh.consume(ArrayUtil.ensureArray(smallArray, 2, "Array too small"));
    }

    // --- ensureArray with custom error message ---

    @Benchmark
    public void ensureArray_withErrorMessage(Blackhole bh) {
        bh.consume(ArrayUtil.ensureArray(smallArray, "Must have elements"));
    }
}
