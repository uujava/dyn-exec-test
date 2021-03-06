package ru.programpark.tests.perf.basic;

import org.jruby.RubyInstanceConfig;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.ScriptingContainer;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.FileReader;
import java.util.concurrent.TimeUnit;

/**
 * Created by kozyr on 05.02.2015.
 */
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
public class RubyBasicContainerTest {

	private ScriptingContainer container;

	@Param({
			"empty_method_test.rb",
			"time_test.rb",
			"system_time_test.rb"
	})
	String script;

	private Runnable test;

	@Setup(Level.Trial)
	public void setUp() throws Exception {
		container = new ScriptingContainer(LocalContextScope.SINGLETON, LocalVariableBehavior.TRANSIENT);
		container.setCompileMode(RubyInstanceConfig.CompileMode.JIT);
	}

	@Setup(Level.Iteration)
	public void setScript() throws Exception {
		test = (Runnable) container.runScriptlet(new FileReader("src/main/ruby/" + script), script);
	}


	@Benchmark
	public void runTest(Blackhole bh) {
		test.run();
		bh.consume(test);
	}
}

