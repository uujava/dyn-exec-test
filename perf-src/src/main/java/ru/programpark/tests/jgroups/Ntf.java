package ru.programpark.tests.jgroups;

import org.nustaq.serialization.FSTConfiguration;
import org.openjdk.jmh.annotations.Setup;
import ru.programpark.tests.dao.Node;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 31.01.2015.
 */

public class Ntf implements Serializable {

	private final static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

	static {
		conf.registerClass(Ntf.class, Date.class, Long.class, Integer.class, Boolean.class, Double.class);
	}

	private static volatile long NTFID = 0;
	public static volatile long SID = 0;

	private Ntf() {
	}

	public Ntf(Node node, int attr) {
		this.id = NTFID++;
		this.sId = SID;
		this.oId = node.getId();
		this.clSid = node.getClsId();
		this.prId = attr;
		this.data = node.getValue(attr);
	}

	private long id;
	private long sId;
	private long clSid;
	private long oId;
	private long prId;
	private Object data;

	public byte[] toBytes(){
		return conf.asByteArray(this);
	}

	public static Ntf fromBytes(byte[] bytes) {
		return (Ntf) conf.asObject(bytes);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Ntf ntf = (Ntf) o;

		if (clSid != ntf.clSid) return false;
		if (id != ntf.id) return false;
		if (oId != ntf.oId) return false;
		if (prId != ntf.prId) return false;
		if (sId != ntf.sId) return false;
		if (data != null ? !data.equals(ntf.data) : ntf.data != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (int) (sId ^ (sId >>> 32));
		return result;
	}
}
