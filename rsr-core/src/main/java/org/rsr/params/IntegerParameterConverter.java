package org.rsr.params;

import java.io.Serializable;

/**
 * Parameter converter used to return an integer value
 * 
 * @author Joe Hudson
 */
public class IntegerParameterConverter implements ParameterConverter {

	public Serializable convert(String param) {
		if (null == param || param.length() == 0) return null;
		return new Integer(param);
	}

}
