package org.rsr;


import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Pattern;

import org.rsr.serializer.Serializer;

class RouteMapping {

	private Pattern pattern;
	private Executable executable;
	private String mediaType;
	private Serializer serializer;
	private String[] varNames;

	public RouteMapping(Pattern routePattern, String[] varNames,
			Method method, Serializable controller,
			boolean wildcard, String mediaType, Serializer serializer) throws Exception {
		this.init(routePattern, varNames, new MethodExecutable(method, controller),
				mediaType, serializer, controller);
	}

	public RouteMapping(Pattern routePattern, String[] varNames,
			String method, Serializable controller,
			boolean wildcard, String mediaType, Serializer serializer) throws Exception {
		Method bestFit = null;
		int fitType = -1;
		Class<?> clazz = controller.getClass();
		for (Method m : clazz.getMethods()) {
			if (m.getName().equals(method)) {
				int _fitType = doesItMatch(m, varNames.length, wildcard);
				if (_fitType > 0 && _fitType > fitType) {
					bestFit = m;
					fitType = _fitType;
				}
			}
		}

		if (bestFit != null) {
			init(routePattern, varNames, new MethodExecutable(bestFit, controller),
					mediaType, serializer, controller);
		} else {
			throw new IllegalArgumentException("No route method match for '"
					+ method + "' on '" + controller.getClass() + "' with "
					+ varNames.length + " params");
		}
	}

	public RouteMapping(Pattern routePattern, String[] varNames,
			Executable executable, String mediaType, Serializer serializer,
			Serializable controller) throws Exception {
		init(routePattern, varNames, executable, mediaType, serializer,
				controller);
	}

	private void init(Pattern routePattern, String[] varNames,
			Executable executable, String mediaType, Serializer serializer,
			Serializable controller) throws Exception {
		this.executable = executable;
		this.pattern = routePattern;
		this.mediaType = mediaType;
		this.varNames = varNames;
		
		executable.init(varNames);
	}

	private int doesItMatch(Method m, int numParams, boolean wildcard) {
		int rtn = -1;
		Class[] sig = m.getParameterTypes();
		int index = 0;

		// first param can be Context
		if (sig.length > 1 && sig[0].isAssignableFrom(Context.class)) {
			rtn += 5;
			index++;
		}

		// next String values for route params
		int pIndex = 0;
		while (pIndex < numParams) {
			if (sig.length > index && sig[index].equals(String.class)) {
				// keep going
				pIndex++;
				index++;
			} else {
				return -1;
			}
		}
		rtn += 10;

		if (wildcard) {
			if (sig.length > index && sig[index].isAssignableFrom(String.class)) {
				if (sig.length == index + 1
						|| (sig.length == index + 2 && sig[sig.length - 1]
								.isAssignableFrom(Map.class))) {
					// good
				} else {
					return -1;
				}
			} else {
				return -1;
			}
		}

		// optionally map can be last
		if (sig.length > 0 && sig[sig.length - 1].isAssignableFrom(Map.class)) {
			rtn += 8;
		}

		return rtn;
	}

	public Serializable execute(String route, String[] routeParams, Context context)
			throws Exception {
		return executable.execute(route, routeParams, context);
	}

	public Pattern getPattern() {
		return pattern;
	}

	public String getMediaType() {
		return mediaType;
	}

	public Serializer getSerializer() {
		return serializer;
	}

	public String[] getVarNames() {
		return varNames;
	}
}
