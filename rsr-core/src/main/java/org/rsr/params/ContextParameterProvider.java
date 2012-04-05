package org.rsr.params;

import java.io.Serializable;

import org.rsr.Context;

/**
 * Parameter provider used to return a value derived from the context
 * 
 * @author Joe Hudson
 */
public class ContextParameterProvider implements ParameterProvider<Context> {

	public Class<Context> getObjectClass() {
		return Context.class;
	}

	public Context getValue(Serializable[] routeParams, Context context) {
		return context;
	};
}
