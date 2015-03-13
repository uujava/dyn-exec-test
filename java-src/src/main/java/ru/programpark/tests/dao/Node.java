package ru.programpark.tests.dao;

import java.util.List;

/**
 * Created by kozyr on 27.01.2015.
 */
public interface Node {

	Long getClsId();

	Node getParent();

	void setParent(Node p);

	Node[] getChildrenArray();

	List<Node> getChildrenList();

	void resetChildren(Node[] children);

	Object getValue(int attr);

	Object setValue(int attr, Object value);

	long getLongValue(int attr);

	Integer getId();
}
