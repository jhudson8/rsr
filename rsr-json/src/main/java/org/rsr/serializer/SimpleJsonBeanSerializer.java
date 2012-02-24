package org.rsr.serializer;

import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Date;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.json.simple.JSONValue;

public class SimpleJsonBeanSerializer extends DefaultSerializer {

	private boolean noNulls = true;
	private boolean noClass = true;
	private int deep = 1;

	@Override
	public void serialize(Serializable response, String mediaType,
			OutputStream out) throws Exception {
		boolean handled = super.serialize(response, mediaType, out, false);
		if (!handled) {
			Map data = PropertyUtils.describe(response);
			evaluate(data, 1);
			out.write(JSONValue.toJSONString(data).getBytes());
		}
	}

	protected void evaluate(Map data, int level) throws Exception {
		if (noClass) {
			data.remove("class");
		}
		Map.Entry[] entries = (Map.Entry[]) data.entrySet().toArray(
				new Map.Entry[data.size()]);
		for (Map.Entry entry : entries) {
			if (null == entry.getValue()) {
				if (noNulls)
					data.remove(entry.getKey());
			} else {
				if (isObject(entry.getValue())) {
					if (deep < level) {
						// no more
						data.remove(entry.getKey());
					} else {
						Map childData = PropertyUtils
								.describe(entry.getValue());
						evaluate(childData, level + 1);
						data.put(entry.getKey(), childData);
					}
				}
			}
		}
	}

	protected boolean isObject(Object obj) {
		return !(Number.class.isAssignableFrom(obj.getClass())
				|| obj.getClass().equals(String.class)
				|| Date.class.isAssignableFrom(obj.getClass())
				|| Boolean.class.isAssignableFrom(obj.getClass()));
	}

	public void setNoNulls(Boolean val) {
		this.noNulls = val;
	}

	public void setNoClass(Boolean val) {
		this.noClass = val;
	}

	public void setDeep(Integer val) {
		this.deep = val;
	}
}
