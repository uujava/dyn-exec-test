package ru.programpark.tests.perf.visitor;

import ru.programpark.tests.dao.Node;
import ru.programpark.tests.dao.NodeVisitor;
import ru.programpark.tests.mirah.*;
/**
 * Created by kozyr on 10.02.2015.
 */
public class MirahVisitor extends TestSetup {

	private final NodeVisitor visitor;

	public MirahVisitor() {
		visitor = new TestVisitor();
	}

	@Override
	public String toString() {
		return "SummNodeVisitor{" + attrId +
				", total=" + total +
				'}';
	}

	@Override
	public NodeVisitor visitorInstance() {
		return visitor;
	}
}
