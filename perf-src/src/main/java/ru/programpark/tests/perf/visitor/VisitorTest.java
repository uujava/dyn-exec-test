package ru.programpark.tests.perf.visitor;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import ru.programpark.tests.dao.*;
import ru.programpark.tests.perf.visitor.TestSetup;

import java.util.concurrent.TimeUnit;

/**
 * Created by kozyr on 26.01.2015.
 */
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
public class VisitorTest {

	@Param({
			"RubyVisitor",
			"RubyRubyVisitor",
			"MirahVisitor",
			"MirahLongVisitor",
			"LongSummJavaVisitor",
			"DoubleSummJavaVisitor",
			"GroovyVisitor"
	})
	private String visitorType;

	@Param({
			"5, 10, 20, 1000"
	})
	private String treeLevels;

	@Param({
			"6", //CLSID
			"7" // DOUBLE
	})
	private int attrId;

	private TreeWalker treeWalker;
	private TestSetup setup;

	@Setup(Level.Trial)
	public void setUp() throws Exception {
		setup = (TestSetup) Class.forName("ru.programpark.tests.perf.visitor." + visitorType).newInstance();
		String[] levelSizes = treeLevels.split(",\\s+");
		int[] cardinality = new int[levelSizes.length];
		for (int i = 0; i < cardinality.length; i++) {
			cardinality[i] = Integer.decode(levelSizes[i]);
		}
		setup.init(cardinality);
	}

	@Setup(Level.Iteration)
	public void setAttr() throws Exception {
		setup.setAttr(attrId);
		treeWalker = setup.getTreeWalker();
	}



	@Benchmark
	public void totalSumm(Blackhole bh) {
		NodeVisitor visitor = setup.visitorInstance();
		treeWalker.accept(visitor);
		bh.consume(setup.getTotal());
	}


}
