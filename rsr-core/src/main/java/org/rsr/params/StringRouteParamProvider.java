package org.rsr.params;


import java.io.Serializable;

import org.rsr.Context;


public class StringRouteParamProvider implements ParameterProvider<String> {

	private int index = -1;

	public StringRouteParamProvider(int index) {
		this.index = index;
	}

	public Class<String> getObjectClass() {
		return String.class;
	}

	public String getValue(Serializable[] routeParams, Context context) {
		if (null == routeParams[index]) return null;
		return routeParams[index].toString();
	}
}
