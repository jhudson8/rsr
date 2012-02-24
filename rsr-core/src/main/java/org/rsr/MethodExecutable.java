package org.rsr;


import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.rsr.params.ContextParameterProvider;
import org.rsr.params.IntegerRouteParamProvider;
import org.rsr.params.LongRouteParamProvider;
import org.rsr.params.MappedParamsParameterProvider;
import org.rsr.params.ParameterProvider;
import org.rsr.params.StringRouteParamProvider;

public class MethodExecutable implements Executable {

	private Object target;
	private Method method;
	private List<ParameterProvider<?>> parameterProviders;

	public void init(String[] parameterNames) {
	}

	public MethodExecutable (Method m, Object target) {
		this.method = m;
		int numParameters = 0;
		this.target = target;
		parameterProviders = new ArrayList<ParameterProvider<?>>();
		for (int i=0; i<m.getParameterTypes().length; i++) {
			Class<?> clazz = m.getParameterTypes()[i];
			if (clazz.equals(String.class)) {
				parameterProviders.add(new StringRouteParamProvider(numParameters++));
			} else if (clazz.equals(Long.class)) {
				parameterProviders.add(new LongRouteParamProvider(numParameters++));
				numParameters ++;
			} else if (clazz.equals(Integer.class)) {
				parameterProviders.add(new IntegerRouteParamProvider(numParameters++));
				numParameters ++;
			} else if (clazz.equals(Context.class)) {
				parameterProviders.add(new ContextParameterProvider());
			} else if (clazz.isAssignableFrom(Map.class)) {
				parameterProviders.add(new MappedParamsParameterProvider());
			} else {
				throw new RuntimeException("Unknown method paramer type: " + clazz.getName());
			}
		}
	}

	public Serializable execute(String route, String[] routeParams, Context context) throws Exception {
		Object[] params = new Object[parameterProviders.size()];
		for (int i = 0; i < params.length; i++) {
			params[i] = parameterProviders.get(i).getValue(routeParams, context);
		}
		return (Serializable) method.invoke(target, params);
	}
}
