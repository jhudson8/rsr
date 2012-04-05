package org.rsr.params;

import java.io.Serializable;

/**
 * Used to abstractly convert parameters
 * 
 * @author Joe Hudson
 */
public interface ParameterConverter {

	/**
	 * Convert the parameter to another format
	 * @param param the parameter
	 * @return the converted value
	 */
	public Serializable convert(String param);
}
