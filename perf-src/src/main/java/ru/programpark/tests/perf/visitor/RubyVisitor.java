package ru.programpark.tests.perf.visitor;

import org.jruby.RubyInstanceConfig;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.ScriptingContainer;
import ru.programpark.tests.dao.Node;
import ru.programpark.tests.dao.NodeVisitor;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by kozyr on 10.02.2015.
 */
public class RubyVisitor extends TestSetup {

	private final ScriptingContainer container;
	private final NodeVisitor visitor;

	public RubyVisitor() throws FileNotFoundException {
		container = new ScriptingContainer(LocalContextScope.SINGLETON, LocalVariableBehavior.TRANSIENT);
		container.setCompileMode(RubyInstanceConfig.CompileMode.JIT);
		String filename = "visitor.rb";
		visitor = (NodeVisitor) container.runScriptlet(new FileReader("perf-src/src/main/ruby/" + filename), filename);
	}

	@Override
	public void setAttr(int attrId) {
		container.callMethod(visitor, "attr", (Integer)attrId);
	}

	@Override
	public NodeVisitor visitorInstance() {
		return visitor;
	}

	@Override
	public Number getTotal() {
		return (Number) container.callMethod(visitor, "total");
	}

}
