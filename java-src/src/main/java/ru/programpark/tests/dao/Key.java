package ru.programpark.tests.dao;


import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by kozyr on 13.02.2015.
 */
public class Key implements Serializable, Comparable<Key> {
	//	private static CompactMap<Key> pool = new CompactMap<Key>(50000);
	private static HashMap<Key, Key> pool = new HashMap<Key, Key>(0);
	private static volatile boolean pooling = false;

	public final long value;

	/**
	 * Конструктор приватный, т.к. должен использоваться фабричный метод
	 * {@link #newKey(long)}
	 */
	private Key(long value) {
		this.value = value;
	}

	/**
	 * Фабричный метод для создания ключа
	 * @param payload значение уникального идентификатора
	 * @return ключ с заданным идентификатором
	 */
	public static synchronized Key newKey(long payload) {
		if (!pooling) {
			return new Key(payload);
		}
		Key key = new Key(payload);
//		Key o = pool.get(payload);
		Key o = pool.get(key);
		if (o == null) {
			pool.put(key, key);
//			pool.put(payload, key);
			return key;
		}
		return o;
	}

	/** Метод управления пулом */
	public static synchronized void setPooling(boolean pooling) {
//	public static void setPooling(boolean pooling) {
		clearPool();
		if (pooling) {
//			Key.pool = new CompactMap<Key>(0);
			Key.pool = new HashMap<Key, Key>(500000);
		}
		Key.pooling = pooling;
	}

	public static boolean isPooling() {
		return pooling;
	}

	public static synchronized void clearPool() {
//	public static void clearPool() {
//		final CompactMap<Key> pool = Key.pool;
//		final HashMap<Key, Key> pool = Key.pool;
//		if (pool != null) {
//			pool.clear();
//		}
//		SLogger.log("Размер пула ключей в Key: " + getPoolSize() + ", очистка пула");
		Key.pool = new HashMap<Key, Key>(0);
//		GUIUtil.memoryInUse(true);
	}

	public static int getPoolSize() {
//		final CompactMap<Key> pool = Key.pool;
		final HashMap<Key, Key> pool = Key.pool;
		return !pooling || pool == null ? 0 : pool.size();
	}

	public static String toCSVIdList(Collection<Key> keys) {
		final StringBuilder csvList = new StringBuilder();
		int i = 0;
		for (Key key : keys) {
			if (i++ != 0) {
				csvList.append(',');
			}
			csvList.append(Long.toString(key.value));
		}
		return csvList.toString();
	}

	/** Метод сравнения ключей. */
	public boolean equals(Object obj) {
		return obj instanceof Key && ((Key) obj).value == value;
	}

	public int hashCode() {
		return (int) (value ^ (value >>> 32));
	}

	public String toString() {
		return String.valueOf(value);
	}

	public int compareTo(Key key) {
		long v = key.value;
		return value < v ? -1 : value > v ? 1 : 0;
	}
}