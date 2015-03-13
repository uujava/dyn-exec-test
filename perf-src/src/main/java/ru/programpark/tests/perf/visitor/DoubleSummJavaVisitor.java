package ru.programpark.tests.perf.visitor;

import ru.programpark.tests.dao.Node;
import ru.programpark.tests.dao.NodeVisitor;

/**
 * Created by kozyr on 10.02.2015.
 */
public class DoubleSummJavaVisitor extends TestSetup implements NodeVisitor {

	public DoubleSummJavaVisitor() {
	}

	@Override
	public Number getTotal() {
		return total;
	}

	private double total;

	public void visit(Node node) {
		Number value = (Number) node.getValue(attrId);
		total += value.doubleValue();
	}

	@Override
	public String toString() {
		return "SummNodeVisitor{" + attrId +
				", total=" + total +
				'}';
	}

	@Override
	public NodeVisitor visitorInstance() {
		return this;
	}
}
