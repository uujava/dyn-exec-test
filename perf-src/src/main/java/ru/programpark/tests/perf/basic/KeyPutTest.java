package ru.programpark.tests.perf.basic;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import ru.programpark.tests.dao.Key;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
public class KeyPutTest {


	@Param({
			"10", "1000", "1000000", "100000000"
	})
	int size;

	private Random random;
	private Long lvl;
	private Key kvl;
	private ConcurrentHashMap chm;


	@Setup(Level.Iteration)
	public void initHash() throws IllegalAccessException, InvocationTargetException, InstantiationException {
		chm = new ConcurrentHashMap(size);
		random = new Random();
	}

	@Setup(Level.Invocation)
	public void random(){
		lvl = new Long(random.nextLong());
		kvl = Key.newKey(lvl);
	}

	@Benchmark
	public void testKeyPut(Blackhole bh){
		chm.put(kvl, kvl);
		bh.consume(kvl);
	}

	@Benchmark
	public void testLongPut(Blackhole bh){
		chm.put(lvl, lvl);
		bh.consume(kvl);
	}

	@Benchmark
	public void testKeyGet(Blackhole bh){
		Object o = chm.get(kvl);
		bh.consume(o);
	}

	@Benchmark
	public void testLongGet(Blackhole bh){
		Object o = chm.get(lvl);
		bh.consume(o);
	}


}
