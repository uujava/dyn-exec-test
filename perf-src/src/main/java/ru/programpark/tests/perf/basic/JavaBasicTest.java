package ru.programpark.tests.perf.basic;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by kozyr on 05.02.2015.
 */
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
public class JavaBasicTest {
	@Benchmark
	public void system_time_test(Blackhole bh) {
		long a = System.currentTimeMillis();
		bh.consume(System.currentTimeMillis() -a);
	}

	@Benchmark
	public void time_test(Blackhole bh) {
		Date a = new Date();
		bh.consume(new Date().getTime() - a.getTime());
	}

	@Benchmark
	public void empty_method() {
	}

}
