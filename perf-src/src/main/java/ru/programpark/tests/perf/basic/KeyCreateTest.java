package ru.programpark.tests.perf.basic;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import ru.programpark.tests.dao.Key;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
public class KeyCreateTest {

	private Random random;
	private long value;


	@Setup(Level.Trial)
	public void initHash() throws IllegalAccessException, InvocationTargetException, InstantiationException {
		random = new Random();
	}

	@Setup(Level.Invocation)
	public void random(){
		value = random.nextLong();
	}

	@Benchmark
	public void createKey(Blackhole bh){
		bh.consume(Key.newKey(value));
	}

	@Benchmark
	public void createLong(Blackhole bh){
		bh.consume(new Long(value));
	}


}
