package org.rsr.executable;

import java.io.Serializable;

import org.rsr.Context;

/**
 * A executable used to do something which can be associated with a route definition.
 * 
 * @author Joe Hudson
 */
public interface Executable {

	/**
	 * Initialize with the route parameter names
	 * @param parameterNames the names referenced in the route definition
	 */
	public void init(String[] parameterNames) throws Exception;

	/**
	 * Perform the dedicated operation and return a dedicated response
	 * @param route the route string
	 * @param parameters all route parameters (which can be associated ordinally with parameterNames from init)
	 * @param context the execution context
	 * @return a value that is appropriate for the endpoint
	 */
	public Serializable execute(String route, String[] parameters,
			Context context) throws Exception;
}
