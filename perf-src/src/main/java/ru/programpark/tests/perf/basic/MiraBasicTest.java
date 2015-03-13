package ru.programpark.tests.perf.basic;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;
import ru.programpark.tests.mirah.*;


/**
 * Created by kozyr on 10.02.2015.
 */
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
public class MiraBasicTest {

	private SystemTimeTest systemTimeTest;
	private TimeTest timeTest;
	private EmptyMethod emptyTest;

	@Setup(Level.Trial)
	public void initContainer(){
		systemTimeTest = new SystemTimeTest();
		timeTest = new TimeTest();
		emptyTest = new EmptyMethod();
	}

	@Benchmark
	public void system_time_test(Blackhole bh) {
		systemTimeTest.run();
		bh.consume(systemTimeTest);
	}

	@Benchmark
	public void time_test(Blackhole bh) {
		timeTest.run();
		bh.consume(timeTest);
	}

	@Benchmark
	public void empty_method() {
		emptyTest.run();
	}

}
