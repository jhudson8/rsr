package org.rsr;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.rsr.serializer.Serializer;

public class RestHandler implements Serializable {
	private static final long serialVersionUID = 1L;

	public static Pattern namedParam = Pattern.compile(":\\w+");
	public static Pattern splatParam = Pattern.compile("\\*\\w+");
	public static Pattern escapeRegExp = Pattern
			.compile("[-\\[\\]{}()+?.,\\\\^$|#\\s]");

	private static Class<?>[] serializableTypes = new Class<?>[] {
		String.class, Long.class, Integer.class, Double.class, Float.class, Boolean.class	
	};

	private Map<String, Serializable> controllerMap = new HashMap<String, Serializable>();
	private Map<String, ArrayList<RouteMapping>> routeMappings = new HashMap<String, ArrayList<RouteMapping>>();

	public RestHandler() {
		init();
	}

	private void init() {
		routeMappings.put("get", new ArrayList<RouteMapping>());
		routeMappings.put("put", new ArrayList<RouteMapping>());
		routeMappings.put("post", new ArrayList<RouteMapping>());
		routeMappings.put("delete", new ArrayList<RouteMapping>());
	}

	public void addController(Serializable controller) {
		Path pAnn = controller.getClass().getAnnotation(Path.class);
		String prefix = null;
		if (pAnn != null) {
			prefix = pAnn.value();
		}
		String[] defaultMediaType = null;
		Produces prAnn = controller.getClass().getAnnotation(Produces.class);
		if (null != prAnn) {
			defaultMediaType = prAnn.value();
		}
		Serializer defaultSerializer = createSerializer(controller.getClass().getAnnotation(org.rsr.Serializer.class));
		for (Method m : controller.getClass().getMethods()) {
			ArrayList<String> types = new ArrayList<String>();
			if (null != m.getAnnotation(GET.class))
				types.add("get");
			if (null != m.getAnnotation(PUT.class))
				types.add("put");
			if (null != m.getAnnotation(POST.class))
				types.add("post");
			if (null != m.getAnnotation(DELETE.class))
				types.add("delete");
			if (types.size() > 0) {
				pAnn = m.getAnnotation(Path.class);
				String route = null;
				if (null != pAnn) {
					route = ((prefix != null) ? prefix : "") + pAnn.value();
				} else {
					route = prefix;
					if (null == route)
						continue;
				}
				String[] mediaType = null;
				prAnn = controller.getClass().getAnnotation(Produces.class);
				if (null != prAnn)
					mediaType = prAnn.value();
				String mtVal = null;
				if (null == mediaType)
					mediaType = defaultMediaType;
				if (null != mediaType && mediaType.length > 0)
					mtVal = mediaType[0];
				
				Serializer serializer = createSerializer(m.getAnnotation(org.rsr.Serializer.class));
				if (null == serializer) serializer = defaultSerializer;
				for (String type : types) {
					RouteSettings settings = addRoute(route, m, controller, type, mtVal, null);
					if (null != serializer) {
						settings.setSerializer(serializer);
					}
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Serializer createSerializer(org.rsr.Serializer ann) {
		if (null == ann) return null;
		Class clazz = ann.value();
		if (null == clazz || clazz.equals(Serializer.class)) clazz = ann.type();
		if (null == clazz || clazz.equals(Serializer.class)) throw new RestException("Unknown serializer type");
		Serializer serializer = null;
		try {
			serializer = (Serializer) clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
		}
		catch (Exception e) {
			throw new RestException("Can't instantiate serializer '" + clazz.getName() + "'", e);
		}
		String properties = ann.properties();
		if (null != properties && properties.length() > 0) {
			String[] props = properties.split(",");
			for (String prop : props) {
				String[] keyValue = prop.split("=");
				if (keyValue.length == 2) {
					String methodName = "set" + Character.toUpperCase(keyValue[0].charAt(0)) + keyValue[0].substring(1);
					Method m = null;
					Class type = null;
					for (Class<?> typeClass : serializableTypes) {
						try {
							m = clazz.getMethod(methodName, new Class[]{typeClass});
							type = typeClass;
							break;
						} catch (NoSuchMethodException e) {}
					}
					if (null != m) {
						try {
							m.invoke(serializer, convertValue(keyValue[1], type));
						}
						catch (Exception e) {
							throw new RestException("Unable to set property '" + keyValue[1] + "' on '" + clazz.getName() + "' (" + properties + ")");
						}
					} else {
						throw new RestException("Unable to set property '" + keyValue[1] + "' on '" + clazz.getName() + "' (" + properties + ")");
					}
				} else {
					throw new RestException("Invalid serializer proerties for '" + clazz.getName() + "' (" + properties + ")");
				}
			}
		}
		return serializer;
	}

	@SuppressWarnings("rawtypes")
	public Object convertValue(String s, Class type) {
		if (null == s || s.length() == 0) return null;
		if (type.equals(String.class)) return s;
		if (type.equals(Long.class)) return new Long(s);
		if (type.equals(Integer.class)) return new Integer(s);
		if (type.equals(Float.class)) return new Float(s);
		if (type.equals(Double.class)) return new Double(s);
		if (type.equals(Boolean.class)) return new Boolean(s);
		throw new RestException("Invalid parameter type: " + type.getName());
	}

	public RouteSettings addRoute(String route, Executable executable, String type) {
		try {
			RoutePatternInfo routeInfo = compileRoute(route);
			RouteMapping routeMapping = new RouteMapping(routeInfo.pattern,
					routeInfo.varNames, executable);
			routeMappings.get(type).add(routeMapping);
			return routeMapping.getSettings();
		} catch (Exception e) {
			throw new RestException(e);
		}
	}

	protected void addRoute(String route, String methodName, String type,
			String mediaType, Serializer serializer) {
		addRoute(route, methodName, this, type, mediaType, serializer);
	}

	protected void addRoute(String route, String methodName,
			String controllerName, String type, String mediaType,
			Serializer serializer) {
		Serializable controller = controllerMap.get(controllerName);
		if (null == controller) {
			throw new IllegalArgumentException(
					"Invalid route controller name '" + controllerName + "'");
		}
		addRoute(route, methodName, controller, type, mediaType, serializer);
	}

	protected void addRoute(String route, String methodName,
			Serializable controller, String type, String mediaType,
			Serializer serializer) {
		try {
			RoutePatternInfo routeInfo = compileRoute(route);
			RouteMapping routeMapping = new RouteMapping(routeInfo.pattern,
					routeInfo.varNames, methodName, controller,
					routeInfo.wildcard);
			routeMapping.getSettings().setMediaType(mediaType)
					.setSerializer(serializer);
			routeMappings.get(type).add(routeMapping);
		} catch (Exception e) {
			throw new RestException(e);
		}
	}

	protected RouteSettings addRoute(String route, Method method,
			Serializable controller, String type, String mediaType,
			Serializer serializer) {
		try {
			RoutePatternInfo routeInfo = compileRoute(route);
			RouteMapping routeMapping = new RouteMapping(routeInfo.pattern,
					routeInfo.varNames, method, controller, routeInfo.wildcard);
			routeMapping.getSettings().setMediaType(mediaType)
					.setSerializer(serializer);
			routeMappings.get(type).add(routeMapping);
			return routeMapping.getSettings();
		} catch (Exception e) {
			throw new RestException(e);
		}
	}

	public RestResponse onRoute(String route, Context context, String type) {
		try {
			List<RouteMapping> mappings = routeMappings.get(type);
			if (null != mappings) {
				for (RouteMapping mapping : mappings) {
					Pattern pattern = mapping.getPattern();
					Matcher matcher = pattern.matcher(route);
					if (matcher.matches()) {
						String[] params = getRouteParams(route, matcher);
						String[] varNames = mapping.getVarNames();
						Map<String, String> routeParams = new HashMap<String, String>();
						for (int i = 0; i < params.length; i++) {
							if (null != params[i]) {
								if (params[i].length() == 0) {
									params[i] = null;
								} else {
									routeParams.put(varNames[i], params[i]);
								}
							}
						}
						context.setRouteParameters(routeParams);
						Serializable rtn = mapping.execute(route, params,
								context);
						return new RestResponse(rtn, mapping.getSerializer(),
								mapping.getMediaType());
					}
				}
			}
			return null;
		} catch (Exception e) {
			throw new RestException(e);
		}
	}

	private static String[] getRouteParams(String route, Matcher routeMatcher) {
		if (routeMatcher.matches()) {
			int groupCount = routeMatcher.groupCount();
			String[] arr = new String[groupCount];
			for (int i = 0; i < groupCount; i++) {
				String groupStr = routeMatcher.group(i + 1);
				arr[i] = groupStr;
			}
			return arr;
		} else {
			return null;
		}
	}

	private static RoutePatternInfo compileRoute(String route) {
		route = escapeRegExp.matcher(route).replaceAll("\\\\$&");
		Matcher namedParamMatcher = namedParam.matcher(route);
		List<String> varNames = new ArrayList<String>();
		while (namedParamMatcher.find()) {
			varNames.add(namedParamMatcher.group().substring(1));
		}
		namedParamMatcher.reset();
		route = namedParamMatcher.replaceAll("([^\\/]+)");
		Matcher splatParamMatcher = splatParam.matcher(route);
		boolean wildcard = false;
		if (splatParamMatcher.find()) {
			varNames.add(splatParamMatcher.group().substring(1));
			wildcard = true;
			splatParamMatcher.reset();
			route = splatParamMatcher.replaceAll("(.*?)");
		}
		Pattern pattern = Pattern.compile(route + "$");
		return new RoutePatternInfo(pattern,
				varNames.toArray(new String[varNames.size()]), wildcard);
	}

	private static class RoutePatternInfo {
		public Pattern pattern;
		public String[] varNames;
		public boolean wildcard;

		public RoutePatternInfo(Pattern pattern, String[] varNames,
				boolean wildcard) {
			this.pattern = pattern;
			this.wildcard = wildcard;
			this.varNames = varNames;
		}
	}
}
