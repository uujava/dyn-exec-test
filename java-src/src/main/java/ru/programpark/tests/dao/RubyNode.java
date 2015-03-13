package ru.programpark.tests.dao;

import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.ScriptingContainer;
import org.jruby.runtime.builtin.IRubyObject;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static ru.programpark.tests.dao.ArrayNode.FIELDS.*;

public class RubyNode implements Node {

	private static final RubyNode[] EMPTY_NODES = new RubyNode[0];
	public static final RubyNode ROOT_NODE;

	private static final ScriptingContainer container;

	static {
		Object[] attrs = new Object[FIELDS.values().length];
		attrs[ID.ordinal()] = 0;
		ROOT_NODE = new RubyNode(attrs);
		ROOT_NODE.setParent(ROOT_NODE);
		container = new ScriptingContainer(LocalContextScope.SINGLETON, LocalVariableBehavior.TRANSIENT);
		container.setCompileMode(RubyInstanceConfig.CompileMode.JIT);

	}

	public static Node getRoot(){
		return ROOT_NODE;
	}

	private Object[] attrs;

	private RubyNode[] children;

	public RubyNode(Object[] attrs) {
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
			children = new RubyNode[childIds.length];
			NodeCache nodeCache = NodeCache.instance();
			for (int i = 0; i < children.length; i++) {
				children[i] = (RubyNode) nodeCache.getNode(childIds[i]);
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
		return (Long) getValue(attr);
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
			public Object next() {
				return eval("rand 100000");
			}
		},
		DATE {
			public Object next() {
				return eval("Time.at " + rnd.nextLong());
			}
		},

		BOOLEAN {
			public Object next() {
				return  eval("(rand > 0.5) ? true : false");
			}

		},

		DOUBLE {
			public Object next() {
				return  eval("rand");
			}
		},

		CLSID {
			public Object next() {
				return eval("rand 1000");
			}
		};
		public Object next() {
			return null;
		}

		public <T> T value(Node node){
			return  (T) node.getValue(ordinal());
		}

		private static Random rnd = new Random();

		private static IRubyObject eval(String script){
			return Ruby.getGlobalRuntime().evalScriptlet(script);
		}
	}

}

