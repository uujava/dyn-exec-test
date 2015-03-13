package ru.programpark.tests.dao;

/**
 * Created by kozyr on 02.02.2015.
 */
public interface FieldEnum {
	public Object next();

	public <T> T value(Node node);

	public int ordinal();

}
