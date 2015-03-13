package ru.programpark.tests.perf.visitor;

import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.ScriptingContainer;
import org.jruby.javasupport.JavaUtil;
import org.jruby.runtime.builtin.IRubyObject;
import ru.programpark.tests.dao.NodeVisitor;
import ru.programpark.tests.dao.RubyNode;
import ru.programpark.tests.perf.TestHelper;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by kozyr on 10.02.2015.
 */
public class RubyRubyVisitor extends TestSetup {

	private final NodeVisitor visitor;
	private final Ruby globalRuntime;

	public RubyRubyVisitor() throws Exception {

		String filename = "visitor.rb";
		FileReader reader = new FileReader("perf-src/src/main/ruby/" + filename);
		StringBuilder buffer = new StringBuilder();
		int numCharsRead;
		char[] arr = new char[8 * 1024];
		while ((numCharsRead = reader.read(arr, 0, arr.length)) != -1) {
			buffer.append(arr, 0, numCharsRead);
		}
		reader.close();
		String targetString = buffer.toString();
		globalRuntime = Ruby.getGlobalRuntime();
		visitor = (NodeVisitor) globalRuntime.evalScriptlet(targetString).toJava(NodeVisitor.class);
	}

	@Override
	public void init(int[] cardinality) throws Exception {
		new TestHelper(cardinality, RubyNode.class).initCache();
	}

	@Override
	public void setAttr(int attrId) {
		((IRubyObject) visitor).callMethod(
				globalRuntime.getCurrentContext(),
				"attr",
				JavaUtil.convertJavaToRuby(globalRuntime, attrId)
		);
	}

	@Override
	public NodeVisitor visitorInstance() {
		return visitor;
	}

	@Override
	public Number getTotal() {
		return (Number) ((IRubyObject) visitor).callMethod(
				globalRuntime.getCurrentContext(),
				"total"
		).toJava(Number.class);
	}

}
