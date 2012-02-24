package org.rsr;

import java.util.Map;

public interface Context {

	public void setRouteParameters(Map<String, String> parameters);

	public String getParameter(String name);

	public String[] getParameterValues(String name);
}
