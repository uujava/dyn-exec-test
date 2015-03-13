package ru.programpark.tests.perf.visitor;

import ru.programpark.tests.dao.ArrayNode;
import ru.programpark.tests.dao.NodeCache;
import ru.programpark.tests.dao.NodeVisitor;
import ru.programpark.tests.dao.TreeWalker;
import ru.programpark.tests.perf.TestHelper;

/**
 * Created by kozyr on 10.02.2015.
 */
public abstract class TestSetup {
	protected long total;
	protected int attrId;

	public void init(int[] cardinality) throws Exception {
		TestHelper testHelper = new TestHelper(cardinality, ArrayNode.class);
		testHelper.initCache();
	}

	public void setAttr(int attrId) {
		this.attrId = attrId;
	}

	abstract public NodeVisitor visitorInstance();

	public Number getTotal() {
		return total;
	}

	public TreeWalker getTreeWalker() {
		return new TreeWalker(NodeCache.instance().getNode(0));
	}
}
