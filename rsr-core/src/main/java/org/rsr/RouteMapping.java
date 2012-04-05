package org.rsr;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Pattern;

import org.rsr.executable.Executable;
import org.rsr.executable.MethodExecutable;
import org.rsr.serializer.Serializer;

/**
 * For internal use.  Contains all data required for a route mapping.
 * 
 * @author Joe Hudson
 */
class RouteMapping {

	private Pattern pattern;
	private Executable executable;
	private String mediaType;
	private Serializer serializer;
	private String[] varNames;
	private RouteSettings settings;

	/**
	 * Route mapping for a method
	 * 
	 * @param routePattern the defined route pattern
	 * @param varNames all route variable names (wildcards or standard)
	 * @param method the method 
	 * @param controller the object which contains the method
	 * @param wildcard indicates if the route contains a wildcard match
	 */
	public RouteMapping(Pattern routePattern, String[] varNames, Method method,
			Serializable controller, boolean wildcard) throws Exception {
		this.init(routePattern, varNames, new MethodExecutable(method,
				controller));
	}

	/**
	 * Route mapping for a method (as defined by a name to be found)
	 * 
	 * @param routePattern the defined route pattern
	 * @param varNames all route variable names (wildcards or standard)
	 * @param method the method name
	 * @param controller the object which contains the method
	 * @param wildcard indicates if the route contains a wildcard match
	 */
	public RouteMapping(Pattern routePattern, String[] varNames, String method,
			Serializable controller, boolean wildcard) throws Exception {
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
			init(routePattern, varNames, new MethodExecutable(bestFit,
					controller));
		} else {
			throw new RsrException("No route method match for '"
					+ method + "' on '" + controller.getClass() + "' with "
					+ varNames.length + " params");
		}
	}

	/**
	 * Route mapping for a generic executable
	 * 
	 * @param routePattern the defined route pattern
	 * @param varNames all route variable names (wildcards or standard)
	 * @param executable the executable
	 */
	public RouteMapping(Pattern routePattern, String[] varNames,
			Executable executable) throws Exception {
		init(routePattern, varNames, executable);
	}

	private void init(Pattern routePattern, String[] varNames,
			Executable executable) throws Exception {
		this.executable = executable;
		this.pattern = routePattern;
		this.varNames = varNames;

		executable.init(varNames);
		this.settings = new RouteSettings() {

			public RouteSettings setSerializer(Serializer serializer) {
				RouteMapping.this.serializer = serializer;
				return RouteMapping.this.settings;
			}

			public RouteSettings setMediaType(String mediatype) {
				RouteMapping.this.mediaType = mediatype;
				return RouteMapping.this.settings;
			}
		};
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
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

	/**
	 * execute this route mapping
	 * @param route the actual route (query string)
	 * @param routeParams the route parameters extracted from the query string
	 * @param context the execution context
	 * @return the response directly from the executable result
	 */
	public Serializable execute(String route, String[] routeParams,
			Context context) throws Exception {
		return executable.execute(route, routeParams, context);
	}

	/**
	 * @return the route pattern
	 */
	public Pattern getPattern() {
		return pattern;
	}

	/**
	 * @return the route media type
	 */
	public String getMediaType() {
		return mediaType;
	}

	/**
	 * @return the route serializer
	 */
	public Serializer getSerializer() {
		return serializer;
	}

	/**
	 * @return the route variable names
	 */
	public String[] getVarNames() {
		return varNames;
	}

	/**
	 * @return the route settings
	 */
	public RouteSettings getSettings() {
		return settings;
	}
}
