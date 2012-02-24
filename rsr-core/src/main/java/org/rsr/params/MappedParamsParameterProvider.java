package org.rsr.params;


import java.io.Serializable;
import java.util.Map;

import org.rsr.Context;


public class MappedParamsParameterProvider implements ParameterProvider<Map> {

	public Class<Map> getObjectClass() {
		return Map.class;
	}

	public Map<String, String> getValue(Serializable[] routeParams, Context context) {
		return context.getNamedParameters();
	};
}
