package org.rsr.params;

import java.io.Serializable;

import org.rsr.Context;

/**
 * Abstractly provide a value from context information like route parameters or execution 
 * context
 * 
 * @author Joe Hudson
 */
public interface ParameterProvider<T> {

	/**
	 * @return the object class representing the conversion type
	 */
	public Class<T> getObjectClass();

	/**
	 * @param routeParams the route parameters
	 * @param context the execution context
	 * @return the converted parameter value
	 */
	public T getValue(Serializable[] routeParams, Context context);
}
