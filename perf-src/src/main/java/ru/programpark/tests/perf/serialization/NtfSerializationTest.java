package ru.programpark.tests.perf.serialization;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import ru.programpark.tests.dao.ArrayNode;
import ru.programpark.tests.dao.Node;
import ru.programpark.tests.jgroups.Ntf;
import ru.programpark.tests.perf.TestHelper;

import java.util.concurrent.TimeUnit;

/**
 * Created by user on 01.02.2015.
 */
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
public class NtfSerializationTest {

	private Node node;

	@Setup(Level.Trial)
	public void setUp() throws Exception {
		new TestHelper(new int[]{5}, ArrayNode.class).initCache();
	}

	@Setup(Level.Invocation)
	public void setNode() {
		this.node = TestHelper.nextNode();
//		System.out.println("node = " + node + " id: " + id);
	}

	@Benchmark
	public void testIntToBytes(Blackhole bh) {
		Ntf ntf = new Ntf(this.node, ArrayNode.FIELDS.RANDOM.ordinal());
		bh.consume(ntf.toBytes());
	}

	@Benchmark
	public void testDateToBytes(Blackhole bh) {
		Ntf ntf = new Ntf(this.node, ArrayNode.FIELDS.DATE.ordinal());
		bh.consume(ntf.toBytes());
	}

	@Benchmark
	public void testToFromBytes(Blackhole bh) {
		Ntf ntf = new Ntf(this.node, ArrayNode.FIELDS.DATE.ordinal());
		byte[] bytes = ntf.toBytes();
		Ntf _ntf = Ntf.fromBytes(bytes);
		bh.consume(_ntf);
	}

	public static void main(String[] args) throws Exception {
		NtfSerializationTest ntfSerializationTest = new NtfSerializationTest();
		ntfSerializationTest.setUp();
		ntfSerializationTest.setNode();
		ntfSerializationTest.testDateToBytes(null);
	}
}
