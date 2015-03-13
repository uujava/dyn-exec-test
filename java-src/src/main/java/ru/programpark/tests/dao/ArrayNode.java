package ru.programpark.tests.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static ru.programpark.tests.dao.ArrayNode.FIELDS.*;

public class ArrayNode implements Node {

	private static final ArrayNode[] EMPTY_NODES = new ArrayNode[0];
	public static final ArrayNode ROOT_NODE;

	static {
		Object[] attrs = new Object[FIELDS.values().length];
		attrs[ID.ordinal()] = 0;
		ROOT_NODE = new ArrayNode(attrs);
		ROOT_NODE.setParent(ROOT_NODE);
	}

	public static Node getRoot(){
		return ROOT_NODE;
	}

	private Object[] attrs;

	private ArrayNode[] children;

	public ArrayNode(Object[] attrs) {
		this.attrs = attrs;
		if (this.attrs[ID.ordinal()] == null) {
			this.attrs[ID.ordinal()] = NodeCache.nextId();
		}
		NodeCache.instance().setNode(this);
	}

	@Override
	public String toString() {
		return "Node{" +
				getId() +
				", p=" + getParent().getId() +
				", children=" + getChildrenArray().length +
				'}';
	}

	@Override
	public List<Node> getChildrenList() {
		return Arrays.asList(getChildrenArray());
	}

	@Override
	public Long getClsId() {
		return (Long) attrs[CLSID.ordinal()];
	}

	@Override
	public Node getParent() {
		Integer parentId = (Integer) attrs[PARENT.ordinal()];
		if(parentId == null) return ROOT_NODE;
		return NodeCache.instance().getNode(parentId);
	}

	@Override
	public void setParent(Node p) {
		attrs[PARENT.ordinal()] = p.getId();
	}

	public void resetChildren(Node[] children) {
		Integer[] arr = new Integer[children.length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = children[i].getId();
		}
		attrs[CHILDREN.ordinal()] = arr;
		this.children = null;
	}

	@Override
	public Node[] getChildrenArray() {
		if (children != null) return children;

		Integer[] childIds = (Integer[]) attrs[CHILDREN.ordinal()];

		if (childIds == null) return EMPTY_NODES;

		if (children == null) {
			children = new ArrayNode[childIds.length];
			NodeCache nodeCache = NodeCache.instance();
			for (int i = 0; i < children.length; i++) {
				children[i] = (ArrayNode) nodeCache.getNode(childIds[i]);
			}
		}
		return children;
	}

	@Override
	public Object getValue(int attr) {
		return attrs[attr];
	}

	@Override
	public Object setValue(int attr, Object value) {
		Object oldValue = attrs[attr];

		if (attr == CHILDREN.ordinal()) {
			children = null;
		}
		attrs[attr] = value;
		return oldValue;
	}

	@Override
	public long getLongValue(int attr) {
		return ((Number) getValue(attr)).longValue();
	}

	@Override
	public Integer getId() {
		return (Integer) attrs[ID.ordinal()];
	}

	public static enum FIELDS implements FieldEnum {

		ID {
			public Object next() {
				return NodeCache.nextId();
			}
		},

		CHILDREN,
		PARENT,
		RANDOM {
			public static final int MAX_RANDOM = 1000000;

			public Object next() {
				return rnd.nextInt(MAX_RANDOM);
			}
		},
		DATE {
			public Object next() {
				return new Date(rnd.nextLong());
			}
		},

		BOOLEAN {
			public Object next() {
				return rnd.nextBoolean();
			}

		},

		DOUBLE {
			public Object next() {
				return rnd.nextDouble();
			}
		},

		CLSID {
			public Object next() {
				return (long)rnd.nextInt(1000);
			}
		};
		public Object next() {
			return null;
		}

		public <T> T value(Node node){
			return  (T) node.getValue(ordinal());
		}

		private static Random rnd = new Random();
	}

}

