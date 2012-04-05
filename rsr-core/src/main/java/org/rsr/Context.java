package org.rsr;

import java.util.Map;

/**
 * Context to be used for a service request.  It is meant to be primary implemented to encapsulate HTTP request
 * data but is not necessary to do so.
 * 
 * @author Joe Hudson
 */
public interface Context {

	/**
	 * Set all parameters that are a part of the route string
	 * @param parameters the route parameters
	 */
	public void setRouteParameters(Map<String, String> parameters);

	/**
	 * @return a parameter value which could either be a route parameter or some other type (like http request parameter).
	 * @param name the parameter name
	 */
	public String getParameter(String name);

	/**
	 * @return all parameter values from either route parameter or other type (like http request parameter).
	 * @param name the parameter name
	 */
	public String[] getParameterValues(String name);
}
