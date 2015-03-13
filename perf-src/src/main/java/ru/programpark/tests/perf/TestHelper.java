package ru.programpark.tests.perf;

import ru.programpark.tests.dao.FieldEnum;
import ru.programpark.tests.dao.Node;
import ru.programpark.tests.dao.NodeCache;
import ru.programpark.tests.dao.NodeVisitor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Random;

/**
 * Created by user on 01.02.2015.
 */
public class TestHelper {
	public static final Random rnd = new Random();
	private final int[] cardinality;
	private Node rootNode;
	private FieldEnum[] fields;
	private Constructor<Node> constructor;

	public TestHelper(int[] cardinality, Class<? extends Node> nodeClass) throws Exception {
		NodeCache.resetCache(cardinality);

		this.cardinality = cardinality;

		Method rootMethod = nodeClass.getDeclaredMethod("getRoot");

		rootNode = (Node) rootMethod.invoke(null);

		if (rootNode == null) throw new IllegalAccessException("Root node is not defiend in class: " + nodeClass);

		Class<?>[] classes = nodeClass.getClasses();

		for (int i = 0; i < classes.length; i++) {
			Class<?> aClass = classes[i];
			if (aClass.isEnum()) {
				fields = (FieldEnum[]) aClass.getEnumConstants();
				break;
			}
		}

		constructor = (Constructor<Node>) nodeClass.getDeclaredConstructor((new Object[0]).getClass());

		if (fields == null) throw new IllegalAccessException("Fields are not defiend in class: " + nodeClass);

	}


	public void initCache() throws Exception {
		rootNode.resetChildren(new Node[0]);
		fillLevel(new Node[]{rootNode}, cardinality, 0);
		NodeCache instance = NodeCache.instance();
		instance.setNode(rootNode);
//		System.out.println(instance.info());
	}

	private void fillLevel(Node[] parents, int[] cardinality, int level) throws Exception {
		int totalFoLevel = cardinality[level];
		Node[] children = new Node[totalFoLevel];
		for (int i = 0; i < parents.length; i++) {

			Node parent = parents[i];
			for (int j = 0; j < totalFoLevel; j++) {
				Node node = constructor.newInstance(new Object[]{generateAttrs()});
				if (parent != null) node.setParent(parent);
				children[j] = node;
			}

			if (parent != null) parent.resetChildren(children);

			if (level + 1 < cardinality.length) fillLevel(children, cardinality, level + 1);
		}
	}


	private Object[] generateAttrs() {
		Object[] attrs = new Object[fields.length];
		for (int i = 0; i < fields.length; i++) {
			FieldEnum value = fields[i];
			attrs[value.ordinal()] = value.next();
		}

		return attrs;

	}

	/**
	 * next random node from node cache
	 *
	 * @return
	 */
	public static Node nextNode() {
		int id = rnd.nextInt(NodeCache.lastId()) + 1;
		if (id >= NodeCache.lastId()) id = NodeCache.lastId() - 1;
		return NodeCache.instance().getNode(id);
	}

}
