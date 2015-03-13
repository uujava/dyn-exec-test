package ru.programpark.tests.dao;

/**
* Created by kozyr on 09.02.2015.
*/
public class TreeWalker {

	Node root;

	public TreeWalker(Node root) {
		this.root = root;
	}

	public void accept(NodeVisitor visitor) {
		visitChildren(visitor, root);
	}

	private void visitChildren(NodeVisitor visitor, Node parent) {
		Node[] children = parent.getChildrenArray();
		for (int i = 0; i < children.length; i++) {
			Node child = children[i];
			visitor.visit(child);
			visitChildren(visitor, child);
		}
	}
}
