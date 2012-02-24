package org.rsr.params;


import java.io.Serializable;

import org.rsr.Context;


public class LongRouteParamProvider implements ParameterProvider<Long> {

	private int index = -1;

	public LongRouteParamProvider(int index) {
		this.index = index;
	}

	public Class<Long> getObjectClass() {
		return Long.class;
	}

	public Long getValue(Serializable[] routeParams, Context context) {
		return new Long(routeParams[index].toString());
	}
}
