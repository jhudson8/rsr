package org.rsr.executable;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.rsr.Context;
import org.rsr.RsrException;
import org.rsr.params.ContextParameterProvider;
import org.rsr.params.IntegerRouteParamProvider;
import org.rsr.params.LongRouteParamProvider;
import org.rsr.params.ParameterProvider;
import org.rsr.params.StringRouteParamProvider;
import org.rsr.serializer.AnnotationProvider;

/**
 * Executable implementation for a class method
 * 
 * @author Joe Hudson
 */
public class MethodExecutable implements Executable, AnnotationProvider {

	private Object target;
	private Method method;
	private List<ParameterProvider<?>> parameterProviders;

	public void init(String[] parameterNames) {
	}

	public <T extends Annotation> T getRouteAnnotation(Class<T> annotationClass) {
		return method.getAnnotation(annotationClass);
	}

	/**
	 * @param m the method
	 * @param target the object which contains the method for execution
	 */
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
			} else if (clazz.equals(Integer.class)) {
				parameterProviders.add(new IntegerRouteParamProvider(numParameters++));
			} else if (Context.class.isAssignableFrom(clazz)) {
				parameterProviders.add(new ContextParameterProvider());
			} else {
				throw new RsrException("Unknown method paramer type: " + clazz.getName());
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
