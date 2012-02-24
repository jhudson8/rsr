package org.rsr.params;


import java.io.Serializable;

import org.rsr.Context;


public class IntegerRouteParamProvider implements ParameterProvider<Integer> {

	private int index = -1;

	public IntegerRouteParamProvider(int index) {
		this.index = index;
	}

	public Class<Integer> getObjectClass() {
		return Integer.class;
	}

	public Integer getValue(Serializable[] routeParams, Context context) {
		return new Integer(routeParams[index].toString());
	}
}
