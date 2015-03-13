package ru.programpark.tests.perf.visitor;

import ru.programpark.tests.dao.NodeVisitor;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by kozyr on 10.02.2015.
 */
public class GroovyVisitor extends TestSetup {

	private final NodeVisitor visitor;
	private final ScriptEngine engine;

	public GroovyVisitor() throws FileNotFoundException, ScriptException {
		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("groovy");
		String filename = "visitor.groovy";
		visitor = (NodeVisitor) engine.eval(new FileReader("perf-src/src/main/groovy/" + filename));
	}


	@Override
	public void setAttr(int attrId) {
		try {
			((Invocable) engine).invokeMethod(visitor, "attr", attrId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public NodeVisitor visitorInstance() {
		return visitor;
	}

	@Override
	public Number getTotal() {
		try {
			return (Number) ((Invocable) engine).invokeMethod(visitor, "attr", attrId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
