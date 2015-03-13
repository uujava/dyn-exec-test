package ru.programpark.tests.perf.basic;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

/**
 * Created by kozyr on 05.02.2015.
 */
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
public class GroovyBasicJsrTest {


	@Param({
			"empty_method_test.groovy",
			"time_test.groovy",
			"system_time_test.groovy"
	})
	String script;

	private ScriptEngine engine;
	@Setup(Level.Trial)
	public void initContainer(){
		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("groovy");
//		System.out.println("engine = " + engine);
	}

	private Runnable test;

	@Setup(Level.Iteration)
	public void setScript() throws Exception {
//		System.out.println("script = " + script);
		test = (Runnable) engine.eval(new FileReader("perf-src/src/main/groovy/" + script));
	}


	@Benchmark
	public void runTest(Blackhole bh) {
		test.run();
		bh.consume(test);
	}


}
