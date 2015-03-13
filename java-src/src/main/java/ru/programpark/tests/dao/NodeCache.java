package ru.programpark.tests.dao;

/**
 * Created by kozyr on 26.01.2015.
 */
public class NodeCache implements VizitorAcceptor {

	private static  NodeCache CACHE;
	private static int lastId = 0;

	Node[] nodes;

	public NodeCache(int maxCount) {
		this.nodes = new Node[maxCount];
	}

	public Node getNode(int id){
		return nodes[id];
	}
	public Node setNode(Node newNode){
		return nodes[newNode.getId()] = newNode;
	}

	public static NodeCache instance(){
		return CACHE;
	}

	public static void resetCache(int[] cardinality){
		int count = 1;
		for (int i = 0; i < cardinality.length; i++) {
			int levelCardinaliry = 1;
			for (int j = i; j >= 0; j--) {
				levelCardinaliry *= cardinality[j];
			}
			count  += levelCardinaliry;
		}
		CACHE = new NodeCache(count);
//		System.out.println("cardinality = " + count);
		lastId = 1; // 0 reserved for root node
	}



	public static int nextId() {
		return lastId++;
	}

	public static int lastId() {
		return lastId;
	}

	public void accept(NodeVisitor v){
//		exclude root node
		for (int i = 1; i < nodes.length; i++) {
			v.visit(nodes[i]);
		}
	}

	public String info() {
		return "last id: " + lastId + " size: " + nodes.length + " first: " + nodes[0] + " last: " + nodes[nodes.length-1];
	}
}
