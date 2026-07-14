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
import org.flossware.commons.util.StringUtil;
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
 * JMH benchmarks for {@link StringUtil} operations.
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
public class StringUtilBenchmark {

    private String[] smallArray;
    private String[] mediumArray;
    private String[] largeArray;
    private StringBuilder reusableBuilder;

    @Setup
    public void setUp() {
        smallArray = new String[]{"alpha", "beta", "gamma"};
        mediumArray = new String[20];
        for (int i = 0; i < mediumArray.length; i++) {
            mediumArray[i] = "element-" + i;
        }
        largeArray = new String[100];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = "item-" + i;
        }
        reusableBuilder = new StringBuilder(4096);
    }

    // --- concatWithSeparator benchmarks ---

    @Benchmark
    public void concatWithSeparator_smallArray(Blackhole bh) {
        bh.consume(StringUtil.concatWithSeparator(", ", smallArray));
    }

    @Benchmark
    public void concatWithSeparator_mediumArray(Blackhole bh) {
        bh.consume(StringUtil.concatWithSeparator(", ", mediumArray));
    }

    @Benchmark
    public void concatWithSeparator_largeArray(Blackhole bh) {
        bh.consume(StringUtil.concatWithSeparator(", ", largeArray));
    }

    @Benchmark
    public void concatWithSeparator_withStringBuilder(Blackhole bh) {
        reusableBuilder.setLength(0);
        bh.consume(StringUtil.concatWithSeparator(reusableBuilder, ", ", mediumArray));
    }

    @Benchmark
    public void concatWithSeparator_separatorAtEnd(Blackhole bh) {
        bh.consume(StringUtil.concatWithSeparator(true, "/", smallArray));
    }

    // --- concat benchmarks ---

    @Benchmark
    public void concat_smallArray(Blackhole bh) {
        bh.consume(StringUtil.concat(smallArray));
    }

    @Benchmark
    public void concat_mediumArray(Blackhole bh) {
        bh.consume(StringUtil.concat(mediumArray));
    }

    // --- requireNonBlank benchmarks ---

    @Benchmark
    public void requireNonBlank_validString(Blackhole bh) {
        bh.consume(StringUtil.requireNonBlank("a valid string"));
    }

    @Benchmark
    public void requireNonBlank_withMessage(Blackhole bh) {
        bh.consume(StringUtil.requireNonBlank("a valid string", "must not be blank"));
    }

    // --- isContained benchmarks ---

    @Benchmark
    public void isContained_found(Blackhole bh) {
        bh.consume(StringUtil.isContained("hello world example", "world"));
    }

    @Benchmark
    public void isContained_notFound(Blackhole bh) {
        bh.consume(StringUtil.isContained("hello world example", "missing"));
    }

    // --- generateUniqueString benchmarks ---

    @Benchmark
    public void generateUniqueString_noArgs(Blackhole bh) {
        bh.consume(StringUtil.generateUniqueString());
    }

    @Benchmark
    public void generateUniqueString_withPrefix(Blackhole bh) {
        bh.consume(StringUtil.generateUniqueString("prefix-"));
    }

    @Benchmark
    public void generateUniqueString_withPrefixAndSuffix(Blackhole bh) {
        bh.consume(StringUtil.generateUniqueString("prefix-", "-suffix"));
    }
}
