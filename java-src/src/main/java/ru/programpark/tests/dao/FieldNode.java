package ru.programpark.tests.dao;

import java.util.*;

/**
 * Created by kozyr on 27.01.2015.
 */
public class FieldNode implements Node {

	private Node parent;
	private List<Node> children;
	private Integer id;
	private Integer rValue;
	private Boolean bValue;
	private Double dValue;
	private Date dateValue;
	private Long clSid;

	public FieldNode(Long clSid) {
		this.clSid = clSid;
	}

	@Override
	public Long getClsId() {
		return clSid;
	}

	@Override
	public Node getParent() {
		return parent;
	}

	@Override
	public void setParent(Node p) {
		parent = p;
	}

	@Override
	public Node[] getChildrenArray() {
		if (children == null) return new Node[0];
		return children.toArray(new Node[children.size()]);
	}

	@Override
	public List<Node> getChildrenList() {
		if (children == null) return Collections.emptyList();
		return children;
	}

	@Override
	public void resetChildren(Node[] children) {
		this.children = Arrays.asList(children);
	}

	@Override
	public Object getValue(int attr) {
		return FIELDS.values()[attr].value(this);
	}

	@Override
	public long getLongValue(int attr) {
		return (Long) getValue(attr);
	}

	@Override
	public Object setValue(int attr, Object value) {
		Object old = FIELDS.values()[attr].value(this);
		FIELDS.values()[attr].set(this, value);
		return old;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public Object getRandom() {
		return rValue;
	}

	public Object getBoolean() {
		return bValue;
	}

	public Object getDouble() {
		return dValue;
	}

	public Object getDate() {
		return dateValue;
	}

	public static enum FIELDS {

		ID {
			@Override
			public <T> T value(FieldNode node) {
				return (T) node.getId();
			}

			@Override
			public void set(FieldNode fieldNode, Object value) {
				throw new UnsupportedOperationException("id is not modifieable");
			}

			public Object next() {
				return NodeCache.nextId();
			}
		},

		CHILDREN {
			@Override
			public <T> T value(FieldNode node) {
				return (T) node.getChildrenArray();
			}

			@Override
			public void set(FieldNode fieldNode, Object value) {

			}
		},
		PARENT {
			@Override
			public <T> T value(FieldNode node) {
				return (T) node.getParent();
			}

			@Override
			public void set(FieldNode fieldNode, Object value) {
				fieldNode.setParent((Node) value);
			}
		},
		RANDOM {
			public static final int MAX_RANDOM = 1000000;

			public Object next() {
				return rnd.nextInt(MAX_RANDOM);
			}

			@Override
			public <T> T value(FieldNode node) {
				return (T) node.rValue;
			}

			@Override
			public void set(FieldNode fieldNode, Object value) {
				fieldNode.rValue = (Integer) value;
			}
		},
		DATE {
			public Object next() {
				return new Date(rnd.nextLong());
			}

			@Override
			public <T> T value(FieldNode node) {
				return (T) node.dateValue;
			}

			@Override
			public void set(FieldNode fieldNode, Object value) {
				fieldNode.dateValue = (Date) value;
			}
		},

		BOOLEAN {
			public Object next() {
				return rnd.nextBoolean();
			}

			@Override
			public <T> T value(FieldNode node) {
				return (T) node.bValue;
			}

			@Override
			public void set(FieldNode fieldNode, Object value) {
				fieldNode.bValue = (Boolean) value;
			}
		},

		DOUBLE {
			@Override
			public void set(FieldNode fieldNode, Object value) {
				fieldNode.dValue = (Double) value;
			}

			public Object next() {
				return rnd.nextDouble();
			}

			@Override
			public <T> T value(FieldNode node) {
				return (T) node.dValue;
			}

		};

		public Object next() {
			return null;
		}

		public abstract <T> T value(FieldNode node);

		private static Random rnd = new Random();

		public abstract void set(FieldNode fieldNode, Object value);
	}
}
