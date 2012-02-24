package org.rsr.params;

import java.io.Serializable;

public class LongParameterConverter implements ParameterConverter {

	public Serializable convert(String param) {
		if (null == param || param.length() == 0) return null;
		return new Long(param);
	}

}
