package org.rsr.params;

import java.io.Serializable;

/**
 * Parameter converter used to return a string value
 * 
 * @author Joe Hudson
 */
public class StringParameterConverter implements ParameterConverter {

	public Serializable convert(String param) {
		if (null == param || param.length() == 0) return null;
		return param;
	}

}
