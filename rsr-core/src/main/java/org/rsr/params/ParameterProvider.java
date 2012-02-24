package org.rsr.params;


import java.io.Serializable;

import org.rsr.Context;


public interface ParameterProvider<T> {

	public Class<T> getObjectClass();

	public T getValue(Serializable[] routeParams, Context context);
}
