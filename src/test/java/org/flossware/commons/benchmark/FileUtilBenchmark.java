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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import org.flossware.commons.util.FileUtil;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

/**
 * JMH benchmarks for {@link FileUtil} operations.
 *
 * <p>Uses temporary files created during setup and cleaned up on teardown.
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
public class FileUtilBenchmark {

    private Path tempDir;
    private Path tempFile;
    private Path tempSubDir;
    private Path tempFileInSubDir;

    @Setup(Level.Trial)
    public void setUp() throws IOException {
        tempDir = Files.createTempDirectory("fileutil-bench");
        tempFile = Files.createTempFile(tempDir, "bench-", ".txt");
        Files.writeString(tempFile, "benchmark test content for FileUtil operations");

        tempSubDir = Files.createDirectory(tempDir.resolve("subdir"));
        tempFileInSubDir = Files.createTempFile(tempSubDir, "nested-", ".txt");
        Files.writeString(tempFileInSubDir, "nested file content");
    }

    @TearDown(Level.Trial)
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFileInSubDir);
        Files.deleteIfExists(tempSubDir);
        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(tempDir);
    }

    // --- requireExists benchmarks ---

    @Benchmark
    public void requireExists_path(Blackhole bh) {
        bh.consume(FileUtil.requireExists(tempFile));
    }

    @Benchmark
    public void requireExists_string(Blackhole bh) {
        bh.consume(FileUtil.requireExists(tempFile.toString()));
    }

    // --- requireRegularFile benchmark ---

    @Benchmark
    public void requireRegularFile(Blackhole bh) {
        bh.consume(FileUtil.requireRegularFile(tempFile));
    }

    // --- requireDirectory benchmark ---

    @Benchmark
    public void requireDirectory(Blackhole bh) {
        bh.consume(FileUtil.requireDirectory(tempDir));
    }

    // --- requireReadable benchmark ---

    @Benchmark
    public void requireReadable(Blackhole bh) {
        bh.consume(FileUtil.requireReadable(tempFile));
    }

    // --- requireWritable benchmark ---

    @Benchmark
    public void requireWritable(Blackhole bh) {
        bh.consume(FileUtil.requireWritable(tempFile));
    }

    // --- newInputStream benchmarks ---

    @Benchmark
    public void newInputStream_path(Blackhole bh) throws IOException {
        try (InputStream is = FileUtil.newInputStream(tempFile)) {
            bh.consume(is);
        }
    }

    @Benchmark
    public void newInputStream_string(Blackhole bh) throws IOException {
        try (InputStream is = FileUtil.newInputStream(tempFile.toString())) {
            bh.consume(is);
        }
    }

    @Benchmark
    public void newInputStream_withBaseDirectory(Blackhole bh) throws IOException {
        try (InputStream is = FileUtil.newInputStream(tempFile, tempDir)) {
            bh.consume(is);
        }
    }

    // --- validatePathTraversal benchmark ---

    @Benchmark
    public void validatePathTraversal(Blackhole bh) {
        bh.consume(FileUtil.validatePathTraversal(tempFile, tempDir));
    }

    // --- validateNoTraversalPatterns benchmark ---

    @Benchmark
    public void validateNoTraversalPatterns(Blackhole bh) {
        FileUtil.validateNoTraversalPatterns(tempFile);
        bh.consume(tempFile);
    }
}
