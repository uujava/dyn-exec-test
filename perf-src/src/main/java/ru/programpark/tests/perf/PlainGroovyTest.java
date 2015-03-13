package ru.programpark.tests.perf;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

public class PlainGroovyTest {


	private ScriptEngine engine;

	public void initContainer(){
		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("groovy");
	}

	private Runnable test;

	public void setScript(String script) throws Exception {
		test = (Runnable) engine.eval(new FileReader("src/main/groovy/" + script));
	}


	public void runTest() {
		test.run();
	}

	public static void main(String[] args) throws Exception {
		PlainGroovyTest test = new PlainGroovyTest();
		test.initContainer();
		test.setScript("empty_method_test.groovy");
		test.runTest();
		System.out.println("test = " + test.test);
   }
}
